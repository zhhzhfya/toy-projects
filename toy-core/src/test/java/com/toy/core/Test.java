package com.toy.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Test {

	public static void main2(String[] args) {
		File dir = new File("/Users/zzy/Documents/work/workspaces/toy-core.git/src/main/java/com/toy");
		File[] folders = dir.listFiles();
		Observable.from(folders).flatMap(new Func1<File, Observable<File>>() {
			@Override
			public Observable<File> call(File file) {
				if (file.isDirectory()) {
					return Observable.from(file.listFiles());
				} else {
					return Observable.just(file);
				}
			}
		}).filter(new Func1<File, Boolean>() {
			@Override
			public Boolean call(File file) {
				return file.getName().endsWith(".java");
			}
		}).map(new Func1<File, File>() {
			@Override
			public File call(File file) {
				return file;
			}
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new Action1<File>() {
			@Override
			public void call(File bitmap) {
				System.out.println("显示" + bitmap.getName());
			}
		});
		System.out.println(111);

	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// System.out.println(Test.MD5("20121221"));
		// System.out.println(Test.MD5("加密"));
		Observable<Boolean> shazi = Observable.just(null).subscribeOn(Schedulers.computation()).flatMap(b -> {
			if (true) {
				System.out.println("沙子水泥都ok");
			}
			return Observable.just(true);
		});

		Observable<Boolean> tuzhi = shazi.subscribeOn(Schedulers.computation()).flatMap(b -> {
			System.out.println("建筑图纸都ok");
			return Observable.just(true);
		});

		Observable<Boolean> beginGaiFang = tuzhi.subscribeOn(Schedulers.computation()).flatMap(b -> {
			System.out.println("盖房");
			System.out.println("盖房ok");
			return Observable.just(true);
		});

		beginGaiFang.subscribe(data -> {
			//
		}, err -> {
			//
		}, () -> {
			//
		});

		// 盖房的步骤
		Observable<String> ob = Observable.create(str -> {
			// 首先进行jdbc查询，返回了abcdef
				str.onNext("沙土");
				str.onNext("钢筋");
				str.onNext("水泥");
				str.onNext("石子儿");
				str.onCompleted();
			}).map(s -> {
			// 初步加工
				if (s.equals("石子儿")) {
					return "小" + s;
				} else {
					return s.toString();
				}
			});
		Observable<String> tz = Observable.create(z -> {
			z.onNext("一层图纸");
			z.onNext("二层图纸");
			z.onNext("三层图纸");
			z.onCompleted();
		});
		Observable<Vector> pzz = Observable.create(z -> {
			// 申请批准房屋建设证
				Vector<String> v = new Vector();
				z.onNext(v);
				z.onCompleted();
			});

		Observable<Object> doTwo = Observable.concat(ob, tz, pzz);
		doTwo.subscribe(obj -> {
			if (obj instanceof Vector) {
				System.out.println("批准房屋建设" + obj);
			}
			if (obj instanceof String) {
				System.out.println(obj);
			}
		}, e -> {
			
		}, () -> {

		});
		// 3天之后开始盖房
		doTwo.delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe(sub -> {

		}, e -> {
		}, () -> {
			System.out.println("1天之后开始盖房");
		});

//		Observable.interval(2, TimeUnit.SECONDS).map(new Func1<Long, String>() {
//			@Override
//			public String call(Long aLong) {
//				return "a";
//			}
//		}).subscribe(s -> {
//			System.out.println(s);
//		});
		List<String> l = Arrays.asList("a", "b");
		l.stream().map(s ->s.toUpperCase()).filter(s -> s.length() == 1);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
