package com.toy.respontory.abservable;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import rx.Observable;

public class MemoryCacheObservable extends CacheObservable {

	private static Logger logger = Logger.getLogger(MemoryCacheObservable.class.getName());
	public static final int DEFAULT_CACHE_SIZE = (24 /* MiB */* 1024 * 1024);
	private static Map<String, JsonObject> cache = new HashMap<String, JsonObject>();
	private Vertx vertx;

	public MemoryCacheObservable(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public Observable<JsonObject> getObservable(JsonObject param) {
		return Observable.create(subscriber -> {
			logger.info("search in memory");
			if (!subscriber.isUnsubscribed()) {
				subscriber.onNext(new JsonObject());
				subscriber.onCompleted();
			}
		});
	}

	public void putData(JsonObject data) {
		cache.put(data.getString("_PK_"), data);
	}
}