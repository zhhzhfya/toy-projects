package com.toy.dev.verticle;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		vertx.deployVerticle(SysWorker.class.getName());
		vertx.deployVerticle(MetaVerticle.class.getName());
	}

}
