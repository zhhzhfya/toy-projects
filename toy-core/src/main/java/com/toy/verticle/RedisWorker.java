package com.toy.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisWorker extends AbstractVerticle {

	public RedisClient redis;

	public RedisWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		RedisOptions options = new RedisOptions();
		options.setHost(config.getString("host"));
		options.setPort(config.getInteger("port"));
		redis = RedisClient.create(vertx, options);

		redis.set("Test", "hello", r -> {
			if (r.succeeded()) {
				System.out.println("key stored");
				redis.get("key", s -> {
					System.out.println("********** Retrieved value: " + s.result());
				});
			} else {
				System.out.println("********** Connection or Operation Failed " + r.cause());
			}
		});

	}
	
}
