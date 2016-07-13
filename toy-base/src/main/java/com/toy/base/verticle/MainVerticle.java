package com.toy.base.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;

import com.toy.base.utils.Runner;

public class MainVerticle extends AbstractVerticle {

	private static Logger logger = Logger.getLogger(MainVerticle.class.getName());

	public static void main(String[] args) {
		Runner.runClustered(MainVerticle.class);
	}

	@Override
	public void start() throws Exception {

		System.setProperty("vertx.disableFileCaching", "true");
		// 加载系统配置
		Buffer config = vertx.fileSystem().readFileBlocking("auto_deploy_verticles.js");
		Buffer resources_cfg = vertx.fileSystem().readFileBlocking("resources_cfg.js");
		JsonObject jo = new JsonObject(config.toString());
		JsonArray js = jo.getJsonArray("autoDeployVerticles");
		JsonObject resourceCfg = new JsonObject(resources_cfg.toString());
		// 循环发布基础模块
		for (Object object : js) {
			final JsonObject cfg = (JsonObject) object;
			logger.info("发布：" + cfg.getString("class") + "参数：" + cfg.getJsonObject("config"));
			if (cfg.getBoolean("auto_deploy")) {
				DeploymentOptions options = new DeploymentOptions().setWorker(true).setConfig(resourceCfg);
				vertx.deployVerticle(cfg.getString("class"), options, ar -> {
					if (ar.succeeded()) {
						logger.info(cfg.getString("desc", cfg.getString("class")) + "启动完成");
					} else {
						logger.info(cfg.getString("desc", cfg.getString("class")) + "启动失败！" + ar.cause());
					}
				});
			}
		}
	}
}
