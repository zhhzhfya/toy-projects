package com.toy.respontory.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		DeploymentOptions options = new DeploymentOptions().setWorker(true).setConfig(config);
		vertx.deployVerticle(JdbcWorker.class.getName(), options);
		vertx.deployVerticle(LogWorker.class.getName(), options);
		vertx.deployVerticle(RedisWorker.class.getName(), options);
		vertx.deployVerticle(RespontoryWorker.class.getName(), options);
	}
}
