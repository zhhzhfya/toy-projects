package com.toy.respontory.abservable;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import rx.Observable;

import com.toy.util.Constant;


public class DatabaseObservable extends CacheObservable {

	private static Logger logger = Logger.getLogger(J2CacheObservable.class.getName());

	private static Map<String, JsonObject> cache = new HashMap<String, JsonObject>();
	Vertx vertx;
	public DatabaseObservable(Vertx vertx) {
		this.vertx = vertx;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Observable<JsonObject> getObservable(JsonObject param) {
		return Observable.create(subscriber -> {
			logger.info("数据库查找");
			if (param.getString("RESOURCE_TYPE").startsWith("MYSQL")) {
				// 构建sql
				String sql = String.format("select * from %s where %s", param.getString(Constant.PARAM_TABLE),  param.getString(Constant.PARAM_WHERE));
				vertx.eventBus().send("db.query", sql, message -> {
					JsonObject bean = (JsonObject) message.result().body();
					if (!subscriber.isUnsubscribed()) {
						if (message.succeeded()) {
							subscriber.onNext(bean);
							subscriber.onCompleted();
						} else {
							subscriber.onError(message.cause());
						}
					}
				});
			}
		});
	}

	public void putData(JsonObject data) {
		cache.put(data.getString("_PK_"), data);
	}
}