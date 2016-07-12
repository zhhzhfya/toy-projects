package com.toy.core;

import java.util.concurrent.TimeUnit;

import org.hsqldb.persist.Log;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SwtichTest {

	public static void main(String[] args) {
//		Observable.switchOnNext(Observable.create(new Observable.OnSubscribe<Observable<Long>>() {
//			@Override
//			public void call(Subscriber<? super Observable<Long>> subscriber) {
//				for (int i = 1; i < 9; i++) {
//					// 每隔1s发射一个小Observable。小Observable每1s发射一个整数
//					// 第一个小Observable将发射6个整数，第二个将发射3个整数
//					subscriber.onNext(Observable.interval(1000, TimeUnit.MILLISECONDS).take(i == 1 ? 6 : 3));
//					Observable.interval(1000, TimeUnit.MILLISECONDS).take(i == 1 ? 6 : 3).subscribe(l ->{
//						System.err.println(l);
//						System.out.println(l.getClass().getName());
//					});
//					try {
//						Thread.sleep(1000);
//						System.out.println("-------------------------------------");
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		})).subscribe(new Action1<Long>() {
//			@Override
//			public void call(Long s) {
//				System.out.println("onNext:" + s);
//			}
//		});
		
		Observable.create(new Observable.OnSubscribe<Integer>() {
		    @Override
		    public void call(Subscriber<? super Integer> subscriber) {
		        for (int i = 0; i < 10; i++) {
		            int sleep = 100;
		            if (i % 3 == 0) {
		                sleep = 300;
		            }
		            try {
		                Thread.sleep(sleep);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		            subscriber.onNext(i);
		        }
		        subscriber.onCompleted();
		    }
		}).subscribeOn(Schedulers.computation())
		  .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
		  .observeOn(Schedulers.computation())
		  .subscribe(new Subscriber<Integer>() {
		      @Override
		      public void onCompleted() {
		          System.out.println("onCompleted:");
		      }

		      @Override
		      public void onError(Throwable e) {
		          System.out.println("onError:");
		      }
		      @Override
		      public void onNext(Integer integer) {
		          System.out.println("onNext:"+integer);
		      }
		  });
		
		System.out.println();
	}

}
