package com.toy.core.respontory;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.toy.core.util.Constant;

import rx.Observable;

public class J2CacheObservable extends CacheObservable {

	private static Logger logger = Logger.getLogger(J2CacheObservable.class.getName());

	private static Map<String, JsonObject> cache = new HashMap<String, JsonObject>();
	Vertx vertx;

	public J2CacheObservable(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public Observable<JsonObject> getObservable(JsonObject param) {
		return Observable.create(subscriber -> {
			logger.info("缓存查找");
			JsonObject bean = cache.get(param.getString(Constant._PK_));
			if (!subscriber.isUnsubscribed()) {
				subscriber.onNext(bean);
				subscriber.onCompleted();
			}
		});
	}

	public void putData(JsonObject data) {
		cache.put(data.getString(Constant._PK_), data);
	}
}
