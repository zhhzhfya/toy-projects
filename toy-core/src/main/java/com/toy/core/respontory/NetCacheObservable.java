package com.toy.core.respontory;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class NetCacheObservable extends CacheObservable {
	Vertx vertx;

	public NetCacheObservable(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public Observable<JsonObject> getObservable(JsonObject param) {
		return Observable.create(new Observable.OnSubscribe<JsonObject>() {
			@Override
			public void call(Subscriber<? super JsonObject> subscriber) {
				JsonObject data;
				// TODO
//				Bitmap bitmap = null;
//				InputStream inputStream = null;
//				Logger.i("get img on net:" + url);
//				try {
//					final URLConnection con = new URL(url).openConnection();
//					inputStream = con.getInputStream();
//					bitmap = BitmapFactory.decodeStream(inputStream);
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					if (inputStream != null) {
//						try {
//							inputStream.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
				data = new JsonObject();
				if (!subscriber.isUnsubscribed()) {
					subscriber.onNext(data);
					subscriber.onCompleted();
				}
			}
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread());
	}
}