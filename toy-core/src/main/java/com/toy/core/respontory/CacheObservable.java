package com.toy.core.respontory;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import rx.Observable;

public abstract class CacheObservable {
	private Vertx vertx;

	public abstract Observable<JsonObject> getObservable(JsonObject param);
}