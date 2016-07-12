package com.toy.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;

import org.hsqldb.lib.StopWatch;

import com.toy.core.ui.MainUI;
import com.toy.core.util.Runner;
import com.toy.verticle.TimerVerticle;

public class MainVerticle extends AbstractVerticle {

	private static Logger logger = Logger.getLogger(MainVerticle.class.getName());

	public static void main(String[] args) {
		Runner.runClustered(MainVerticle.class);
//		Runner.runClustered(TimerVerticle.class);
	}

	@Override
	public void start() throws Exception {
		
		System.setProperty("vertx.disableFileCaching", "true");
		// 加载系统配置
		Buffer config = vertx.fileSystem().readFileBlocking("auto_deploy_verticles.js");
		JsonObject jo = new JsonObject(config.toString());
		JsonArray js = jo.getJsonArray("autoDeployVerticles");
		// 循环发布基础模块
		for (Object object : js) {
			final JsonObject cfg = (JsonObject) object;
			try {
				Class.forName(cfg.getString("class"));
			} catch (ClassNotFoundException e) {
				logger.info("没有找到类：" + cfg.getString("class"));
				continue;
			}
			logger.info("发布：" + cfg.getString("class") + "参数：" + cfg.getJsonObject("config"));
			DeploymentOptions options = new DeploymentOptions().setWorker(true).setConfig(cfg.getJsonObject("config"));
			vertx.deployVerticle(cfg.getString("class"), options, ar -> {
				if (ar.succeeded()) {
					logger.info(cfg.getString("desc", cfg.getString("class")) + "启动完成");
				} else {
					logger.info(cfg.getString("desc", cfg.getString("class")) + "启动失败！" + ar.cause());
				}
			});
		}

		MessageConsumer<String> consumer = vertx.eventBus().consumer("serv.mgr");
		consumer.handler(message -> {
			JsonObject json = new JsonObject(message.body());
			if ("mygod".equals(json.getString("ACT"))) {
				MainUI frame = new MainUI(vertx);
				frame.setSize(940, 592);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
//		MainUI frame = new MainUI(vertx);
//		frame.setSize(940, 592);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
	}
}