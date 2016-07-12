package com.toy.verticle.sys;

import java.io.File;
import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

import com.toy.core.util.Constant;
import com.toy.verticle.MetaConstant;

public class SyServWorker extends AbstractVerticle {
	private Logger logger = Logger.getLogger(SyServWorker.class.getName());

	public SyServWorker() {
	}

	@Override
	public void start() throws Exception {
		
		MessageConsumer<String> consumer = vertx.eventBus().consumer("serv.mgr");
		consumer.handler(message -> {
			// 按shift鼠标放到getMessage()上
			String s__ = Constant.getMessage();
			SharedData sharedData = vertx.sharedData();
			LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);
			// TODO 根据USER、SCENE_MD5判断用户有效性
			
			JsonObject event = new JsonObject(message.body());
			JsonObject data = event.getJsonObject("DATA");
			for(String key : data.getMap().keySet()){
				// key对应的是数组，并且元定义里有
				if (data.getValue(key) instanceof JsonArray && metaMap.keySet().contains(key)) {
					JsonObject metaJson = (JsonObject) metaMap.get(key);
					JsonArray datas = data.getJsonArray(key);
					for(int i = 0; i < datas.size(); i++){
						JsonObject data0 = datas.getJsonObject(i);
						String servCode = data0.getString("SERV_CODE");
						String path = "doc" + File.separator + key + File.separator + servCode + ".json";
						if (vertx.fileSystem().existsBlocking(path)) {// 修改json文件
							JsonObject oldData = vertx.fileSystem().readFileBlocking(path).toJsonObject();
							for(String key_ : data0.getMap().keySet()){
								oldData.put(key_, data0.getValue(key_));
							}
							data0 = oldData.copy();
						}
						// 循环元数据检查json数据
						for(String keyMeta : metaJson.getMap().keySet()){
							
						}
						vertx.fileSystem().writeFileBlocking(path, Buffer.buffer(data0.toString()));
					}
				} else {
					// 调用业务数据的修改
					if(Constant.actSet.contains(data.getString("ACT"))){
						// TODO 系统通用动作
						systemAction(data);
					} else {
						// TODO 调用用户动作
					}
				}
			}
		
		});
	}

	private void systemAction(JsonObject json) {
		if (Constant.ACT_ADD.equals(json.getString("ACT"))) {
			// 添加动作
			//
			/*
			 * 找SERV_ID->找OBJECT_LINK->找OBJECT 找SERV数据，找关联信息，找对象信息，把Bean进行插入操作
			 * 1、例子：提取参数发消息，比如锁定库存的场景
			 */
		}
		if (Constant.ACT_MODIFY.equals(json.getString("ACT"))) {
			// 修改动作
			/*
			 * 找SERV_ID->找OBJECT_LINK->找OBJECT 找SERV数据，找关联信息，找对象信息，把Bean进行插入操作
			 */
		}
		if (Constant.ACT_DELETE.equals(json.getString("ACT"))) {
			// 删除动作
			/*
			 * 找SERV_ID->找OBJECT_LINK->找OBJECT 找SERV数据，找关联信息，找对象信息，把Bean进行插入操作
			 */
		}
		if (Constant.ACT_QUERY.equals(json.getString("ACT"))) {
			// 查询动作
			/*
			 * 找SERV_ID->找OBJECT_LINK->找OBJECT 找SERV数据，找关联信息，找对象信息，把Bean进行插入操作
			 */
		}
		if (Constant.ACT_EXPORT.equals(json.getString("ACT"))) {
			// 导出动作
			/*
			 * 找SERV_ID->找OBJECT_LINK->找OBJECT 找SERV数据，找关联信息，找对象信息，把Bean进行插入操作
			 */
		}
	}

	@Override
	public void stop() throws Exception {
	}

}
