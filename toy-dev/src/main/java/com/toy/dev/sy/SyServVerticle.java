package com.toy.dev.sy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.util.logging.Logger;

import org.dom4j.DocumentException;

import com.toy.dev.sy.handler.SyHandler;
import com.toy.dev.utils.Constant;
import com.toy.dev.utils.MetaConstant;
import com.toy.dev.utils.MetaFileUtils;

public class SyServVerticle extends AbstractVerticle {
	
	private Logger logger = Logger.getLogger(SyServVerticle.class.getName());

	private static JsonObject metaData;

	private static String servName = "SERV";

	public SyServVerticle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {

		// 读取元定义
		readMetaData();

		// 开启工人
		startWorkers();

		// 开启事件监听
		startEventConsumer();
		// 暂时延时启用，因为端口一样的话和WebWorker的冲突
		//vertx.setTimer(500, id -> {
			// 开启web服务
		startWebService();
		//	});
	}

	
	/**
	 * 读取元数据
	 * 
	 * @throws DocumentException
	 */
	private void readMetaData() throws DocumentException {
		Buffer config = vertx.fileSystem().readFileBlocking("meta/sy_serv.xml");
		metaData = MetaFileUtils.readMeteData(config.toString());
		vertx.eventBus().send(MetaConstant.REG_META, metaData, (message -> {
			logger.info(message.result().body().toString());
		}));
	}

	/**
	 * 启动业务工人
	 */
	private void startWorkers() {
		DeploymentOptions options = new DeploymentOptions().setWorker(true).setInstances(8);
		vertx.deployVerticle(SyServWorker.class.getName(), options);
	}
	
	/**
	 * 启动事件消费
	 */
	private void startEventConsumer() {
		MessageConsumer<String> consumer = vertx.eventBus().consumer("serv.mgr");
		consumer.handler(message -> {
			JsonObject json = new JsonObject(message.body());
			if (Constant.actSet.contains(json.getString("ACT")) && json.containsKey("SERV_ID")) {
				vertx.eventBus().send(servName + "." + json.getString("ACT"), message.body());
			}
			message.reply("resend.message.ok");
		});
	}

	/**
	 * 启动web服务
	 */
	private void startWebService() {
		SharedData sharedData = vertx.sharedData();
		LocalMap<String, Object> syConfig = sharedData.getLocalMap(Constant.SY_CONFIG);
		LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);
		
		Router router = Router.router(vertx);
		router.post("/sy/base/query").handler(SyHandler.create());
		
		BridgeOptions opts = new BridgeOptions();
		// 允许发给服务器的地址
		opts.addInboundPermitted(new PermittedOptions().setAddress("serv.mgr"));
		// 允许发到客户端的地址
		opts.addOutboundPermitted(new PermittedOptions().setAddress("sy_serv.sync"));
		opts.addOutboundPermitted(new PermittedOptions().setAddress("sy_object.sync"));
		opts.addOutboundPermitted(new PermittedOptions().setAddress("sy_object_link.sync"));
		opts.addOutboundPermitted(new PermittedOptions().setAddress("system.timer"));
		// 创建事件桥并添加到路由上
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route(Constant.EVENT_PATH).handler(ebHandler);
		// 设置静态handler
		router.route().handler(StaticHandler.create().setWebRoot("webroot_sys"));

		Integer port = 8080;// (Integer) syConfig.get(Constant.WEB_PORT);
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
		System.out.println(String.format("******************************************************************"));
		System.out.println(String.format("*************** SyServVerticle ok 端口 %d *********************", port));
		System.out.println(String.format("******************************************************************"));
	}
}
