package com.toy.verticle;

import io.netty.util.Constant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.logging.Logger;

public class WebWorker extends AbstractVerticle {

	public WebWorker() {
		// TODO Auto-generated constructor stub
	}

	private static Logger logger = Logger.getLogger(WebWorker.class.getName());

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		// 设置系统参数
		setSyConfig(config);
		
		Router router = Router.router(vertx);
		router.route().handler(StaticHandler.create().setWebRoot("webroot"));
		vertx.createHttpServer().requestHandler(router::accept).listen(config.getInteger("port", 80));

		logger.info("********** webworker ok. ***********");
		logger.info("控制台:http://localhost:"+config.getInteger("port", 80));
	}

	private void setSyConfig(JsonObject config) {
		SharedData sd = vertx.sharedData();
		LocalMap<String, Object> syConfig = sd.getLocalMap(Constant.SY_CONFIG);
		syConfig.put(Constant.WEB_PORT, config.getInteger("port", 80));
	}

}
