package com.toy.dev.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		DeploymentOptions options = new DeploymentOptions().setWorker(true).setConfig(config);
		
		vertx.deployVerticle(SysWorker.class.getName(), options);
		vertx.deployVerticle(MetaVerticle.class.getName(), options);
		vertx.deployVerticle(StorageWorker.class.getName(), options);
	}

}
