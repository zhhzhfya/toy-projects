package com.toy.dev.verticle;

import com.toy.dev.utils.MetaConstant;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

/**
 * 地址管理
 * 
 * @author zzy
 *
 */
public class MetaVerticle extends AbstractVerticle {

	public MetaVerticle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		SharedData sharedData = vertx.sharedData();
		LocalMap<String, Object> address = sharedData.getLocalMap(MetaConstant.REG_ADDRESS);
		// 注册地址：产品.模块.动作
		vertx.eventBus().consumer(MetaConstant.REG_ADDRESS, (message -> {
			JsonObject body = (JsonObject) message.body();
			JsonObject old = (JsonObject) address.putIfAbsent(body.getString(MetaConstant.REG_ADDRESS), body);
			if (old != null) {
				message.reply(new JsonObject().put("flag", true).put("message", "地址已经存在").put("preReg", old));
			} else {
				message.reply(new JsonObject().put("flag", true).put("message", "注册成功"));
			}
		}));

		// 注册元数据
		vertx.eventBus().consumer(MetaConstant.REG_META, (message -> {
			LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);
			JsonObject meta = (JsonObject) message.body();
			// 循环读取元数据存入metaMap，方便使用
			loopMetaJson(metaMap, meta);
			message.reply(new JsonObject().put("flag", true));
		}));
		
//		vertx.setPeriodic(5000, id -> {
//			LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);
//			for (String key : metaMap.keySet()) {
//				System.err.println(key+"**********************>"+metaMap.get(key));
//			}
//
//			});

		// 判断是否元数据
		vertx.eventBus().consumer(MetaConstant.IF_META, (message -> {
			// TODO 担心这里性能
				LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);
				JsonObject meta = (JsonObject) message.body();
				String key = meta.getString(MetaConstant.IF_META);
				if (metaMap.keySet().contains(key)) {
					message.reply(new JsonObject().put("flag", true));
				} else {
					message.reply(new JsonObject().put("flag", false));
				}
			}));
	}

	private void loopMetaJson(LocalMap<String, Object> metaMap, JsonObject meta) {
		for (String key : meta.getMap().keySet()) {
			if (!key.equals("ITEMS") && meta.getValue(key) instanceof JsonObject && meta.getJsonObject(key).containsKey("ITEMS")) {
				metaMap.put(key, meta.getJsonObject(key));
				loopMetaJson(metaMap, meta.getJsonObject(key));
			}
		}
	}
}
