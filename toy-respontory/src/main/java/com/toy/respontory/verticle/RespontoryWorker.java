package com.toy.respontory.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import rx.Observable;

import com.toy.respontory.abservable.Respontory;

public class RespontoryWorker extends AbstractVerticle {

	public RespontoryWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("data.query", message -> {
			JsonObject param = new JsonObject((String) message.body());
			System.out.println("参数：" + param);
			getOjbectObservable(vertx, param).subscribe(bean -> {
				message.reply(bean);
			}, err -> {
				message.reply(err);
			}, () -> {
			});
		});
	}
	
	public Observable<JsonObject> getOjbectObservable(Vertx vertx, JsonObject param) {
		Respontory respontory = new Respontory(vertx);
        // 为了有效查询，创建查询队列
        Observable<JsonObject> source = Observable.concat(
        		respontory.j2cache(param),
        		respontory.database(param))
                .first(data -> data != null);
        return source;
    }

}
