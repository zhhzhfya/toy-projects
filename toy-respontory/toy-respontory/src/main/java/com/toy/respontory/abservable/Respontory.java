package com.toy.respontory.abservable;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;

import rx.Observable;

/**
 * 资源管理
 * 
 * @author zzy
 *
 */
public class Respontory {
	private static Logger logger = Logger.getLogger(Respontory.class.getName());
	/* 内存缓存 */
	private MemoryCacheObservable memoryCacheObservable;
	/* 文件缓存 */
	private DiskCacheObservable diskCacheObservable;
	/* 网络加载缓存 */
	private NetCacheObservable netCacheObservable;
	/* 数据库查询 */
	private DatabaseObservable databaseObservable;
	/* j2cache缓存 */
	private J2CacheObservable j2CacheObservable;
	/* vertx 对象 */
	private Vertx vertx;

	public Respontory(Vertx vertx) {
		memoryCacheObservable = new MemoryCacheObservable(vertx);
		diskCacheObservable = new DiskCacheObservable(vertx);
		netCacheObservable = new NetCacheObservable(vertx);
		j2CacheObservable= new J2CacheObservable(vertx);
		databaseObservable = new DatabaseObservable(vertx);
	}

	public Observable<JsonObject> memory(JsonObject param) {
		return memoryCacheObservable.getObservable(param).compose(logSource("MEMORY"));
	}

	public Observable<JsonObject> disk(JsonObject param) {
		return diskCacheObservable.getObservable(param).filter(data -> data != null).doOnNext(memoryCacheObservable::putData).compose(logSource("DISK"));

	}

	public Observable<JsonObject> network(JsonObject param) {
		return netCacheObservable.getObservable(param).doOnNext(data -> {
			memoryCacheObservable.putData(data);
			diskCacheObservable.putData(data);
		}).compose(logSource("NET"));
	}

	public Observable<JsonObject> database(JsonObject param) {
		return databaseObservable.getObservable(param).doOnNext(data -> {
			j2CacheObservable.putData(data);
		}).compose(logSource("数据库"));
	}
	
	public Observable<JsonObject> j2cache(JsonObject param) {
		return j2CacheObservable.getObservable(param).compose(logSource("j2cache"));
	}

	Observable.Transformer<JsonObject, JsonObject> logSource(final String source) {
		return dataObservable -> dataObservable.doOnNext(data -> {
			if (data != null && data != null) {
				logger.info(source + " 找到数据!");
			} else {
				logger.info(source + " 没有找到数据!");
			}
		});
	}
}