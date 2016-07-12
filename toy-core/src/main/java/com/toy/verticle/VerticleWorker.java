package com.toy.verticle;

import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
//
public class VerticleWorker extends AbstractVerticle {
	
	private Logger logger = Logger.getLogger(VerticleWorker.class.getName());
	
	public VerticleWorker() {
	}

	@Override
	public void start() throws Exception {
		
	}
	
	/**
	 * 发布verticle
	 * @param className
	 * @param config
	 */
	public void deployVerticle(String className, JsonObject config){
		vertx.deployVerticle(className, new DeploymentOptions().setConfig(config));
	}
}
