package com.toy.respontory.abservable;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DiskCacheObservable extends CacheObservable {
	File mCacheFile;
	private static Logger logger = Logger.getLogger(DiskCacheObservable.class.getName());
	Vertx vertx;

	public DiskCacheObservable(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public Observable<JsonObject> getObservable(JsonObject param) {
		return Observable.create(new Observable.OnSubscribe<JsonObject>() {
			@Override
			public void call(Subscriber<? super JsonObject> subscriber) {
				logger.info("read file from disk");
				String url = param.getString("url");
				File f = getFile(url);
				JsonObject data = new JsonObject();
				subscriber.onNext(data);
				subscriber.onCompleted();
			}
		}).subscribeOn(Schedulers.io());
	}

	private File getFile(String url) {
		url = url.replaceAll(File.separator, "-");
		return new File(mCacheFile, url);
	}

	/**
	 * save pictures downloaded from net to disk
	 * 
	 * @param data data to be saved
	 */
	public void putData(JsonObject data) {
		Observable.create(new Observable.OnSubscribe<JsonObject>() {
			@Override
			public void call(Subscriber<? super JsonObject> subscriber) {
				File f = getFile(data.getString("url"));
				OutputStream out = null;
				try {
					// TODO 这里需要重新写代码
					out = new FileOutputStream(f);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (!subscriber.isUnsubscribed()) {
					subscriber.onNext(data);
					subscriber.onCompleted();
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}
}