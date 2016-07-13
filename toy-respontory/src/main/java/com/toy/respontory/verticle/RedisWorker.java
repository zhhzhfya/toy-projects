package com.toy.respontory.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.logging.Logger;

public class RedisWorker extends AbstractVerticle {
	private static Logger logger = Logger.getLogger(RedisWorker.class.getName());
	public RedisClient redis;

	public RedisWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		JsonObject redisCfg = null;
		for (String key : config.getMap().keySet()) {
			if (key.startsWith("redis")) {
				redisCfg = config.getJsonObject(key);
			}
		}
		if (redisCfg == null) {
			logger.warning("没有找到redis的配置文件");
			return;
		}
		RedisOptions options = new RedisOptions();
		options.setHost(redisCfg.getString("host"));
		options.setPort(redisCfg.getInteger("port"));
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
