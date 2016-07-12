package com.toy.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.rxjava.core.Vertx;

import java.awt.Event;
import java.util.Random;

import com.toy.core.util.DateUtils;

public class TimerVerticle extends AbstractVerticle {

	public TimerVerticle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
//		VertxOptions op = new VertxOptions();
//		Vertx.clusteredVertx(op, res ->{
//			Vertx _vertx = res.result();
//			_vertx.setPeriodic(1000, id -> {
//				Random r = new Random();
//				JsonObject machineInfo = new JsonObject();
//				
//				machineInfo.put("cpu", String.valueOf(r.nextInt(100)));
//				machineInfo.put("memery", String.valueOf(r.nextInt(10000)));
//				machineInfo.put("net", String.valueOf(r.nextInt(200)));
//				_vertx.eventBus().publish("system.timer", machineInfo.toString());
//				System.out.println("publish:system.timer:"+machineInfo.toString());
//			});
//		});
		
		long timerID = vertx.setPeriodic(1000, id -> {
			Random r = new Random();
			JsonObject machineInfo = new JsonObject();
			machineInfo.put("cpu", String.valueOf(r.nextInt(100)));
			machineInfo.put("memery", String.valueOf(r.nextInt(10000)));
			machineInfo.put("net", String.valueOf(r.nextInt(200)));
			vertx.eventBus().publish("system.timer", machineInfo.toString());
		});
		
		SharedData sd = vertx.sharedData();
		vertx.setPeriodic(1000, id ->{
			sd.getClusterWideMap("mymap", map ->{
				map.result().put("a", "分布式map值："+new Random().nextInt(), r->{
					if (r.succeeded()) {
						//System.out.println("ok");
					}
				});
			});
			
		});
	}

}
