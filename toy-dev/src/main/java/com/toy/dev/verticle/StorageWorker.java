package com.toy.dev.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;


public class StorageWorker extends AbstractVerticle {

	private Logger logger = Logger.getLogger(StorageWorker.class.getName());

	public StorageWorker() {
	}

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("db.mgr").handler(message -> {
			// 收到数据，根据数据类型和转发规则进行转发存储，并更新缓存
			JsonObject data = (JsonObject) message.body();
			String act = message.headers().get("act");
			if ("save".equals(act)) {
				String obj = data.getString("OBJ_CODE");
				Long pk = data.getLong("$PK_CODE$");
				if (pk != null) {
					// 进行保存
					// 判断存储规则（分区规则、缓存规则）
					// 根据数据类型 发送不同的消息：例如：TOPIC:BJ.DB、SH.DB
					// 更新本地缓存、远程缓存
				} else {
					// 进行插入
					
				}
			}
			if ("delete".equals(act)){
				
			}
			
		});
	}

	@Override
	public void stop() throws Exception {

	}

}
