package com.toy.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class Test2 {
	private static Logger logger = Logger.getLogger(Test2.class.getName());

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(5);
		// 使用publish操作符将普通Observable转换为可连接的Observable
		ConnectableObservable<Long> connectableObservable = obs.publish();
		// 第一个订阅者订阅，不会开始发射数据
		connectableObservable.subscribe(new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				logger.info("1.onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				logger.info("1.onError");
			}

			@Override
			public void onNext(Long along) {
				logger.info("1.onNext:" + along + "->time:" + sdf.format(new Date()));
			}
		});
		// 开始发射数据
		logger.info("start time:" + sdf.format(new Date()));
		connectableObservable.connect();
		// 第二个订阅者延迟2s订阅，这将导致丢失前面2s内发射的数据
		connectableObservable.delaySubscription(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				logger.info("2.onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				logger.info("2.onError");
			}

			@Override
			public void onNext(Long along) {
				logger.info("2.onNext:" + along + "->time:" + sdf.format(new Date()));
			}
		});

		System.out.println();
	}

}
