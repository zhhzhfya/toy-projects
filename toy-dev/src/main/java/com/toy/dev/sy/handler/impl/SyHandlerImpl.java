package com.toy.dev.sy.handler.impl;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.RoutingContext;

import java.util.logging.Logger;

import rx.Observable;
import rx.schedulers.Schedulers;

import com.toy.dev.sy.handler.SyHandler;
import com.toy.dev.utils.Constant;
import com.toy.dev.utils.MetaConstant;

public class SyHandlerImpl implements SyHandler {
	private Logger logger = Logger.getLogger(SyHandlerImpl.class.getName());
	private Vertx vertx;

	public SyHandlerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(RoutingContext routingContext) {
		this.vertx = routingContext.vertx();
		SharedData sharedData = routingContext.vertx().sharedData();
		LocalMap<String, Object> syConfig = sharedData.getLocalMap(Constant.SY_CONFIG);
		LocalMap<String, Object> metaMap = sharedData.getLocalMap(MetaConstant.REG_META);

		StringBuilder builder = new StringBuilder();
		routingContext.request().handler(buffer -> {
			builder.append(buffer.getString(0, buffer.length()));
		}).endHandler(wtf -> {
			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			JsonObject params = new JsonObject(builder.toString());
			JsonObject back = new JsonObject();
				// 开始执行一次读取readMeta, readDict, readData....
				Observable.concat(readMeta(metaMap, params),
								readView(metaMap, params),
								  readDict("SY_YESNO"), 
							      readData(params)
									).subscribeOn(Schedulers.computation()).subscribe(json -> {
					back.getMap().putAll(json.getMap());
				}, e -> {
					logger.warning("系统异常：" + e.getLocalizedMessage());
				}, () -> {
					response.write(back.toString());
					routingContext.response().end();
				});
			});
	}
	
	/**
	 * 加载视图需要的json数据
	 * @param metaMap
	 * @param param
	 * @return
	 */
	public Observable<JsonObject> readView(LocalMap<String, Object> metaMap, JsonObject param){
		Observable<JsonObject> ob = Observable.create(p -> {
			String jsonPath = "src/main/resources/doc/SY_SERV/" + param.getString("META") + ".json";
			if (vertx.fileSystem().existsBlocking(jsonPath)) {
				vertx.fileSystem().readFile("src/main/resources/doc/SY_SERV/" + param.getString("META") + ".json", result -> {
					if (result.succeeded()) {
						p.onNext(new JsonObject().put("VIEW", new JsonObject(result.result().toString())));
						p.onCompleted();
					} else {
						logger.warning(result.cause().getLocalizedMessage());
					}
				});
			} else {
				// 如果json不存在则从meta里提取，并存储
				if (metaMap.keySet().contains(param.getString("META"))) {
					JsonObject meta = (JsonObject) metaMap.get(param.getString("META"));
					JsonArray items = meta.getJsonArray("ITEMS");
					JsonObject _cols = new JsonObject();
					for (int i = 0; i < items.size(); i++) {
						JsonObject item = items.getJsonObject(i);
						JsonObject temp = new JsonObject();
						temp.put("ITEM_NAME", item.getString("NAME"));
						temp.put("ITEM_CODE", item.getString("ITEM_CODE"));
						temp.put("ITEM_LIST_FLAG", "1");
						_cols.put(item.getString("ITEM_CODE"), temp);
					}
					JsonObject view = new JsonObject();
					view.put("_COLS_", _cols);// 列信息
					view.put("_QUICK_SEARCH_", "[]");// 快速查询字段
					view.put("_COMMON_SEARCH_", "[]");// 普通查询字段
					view.put("_LIST_BUTTONS_", "[]");// 列表按钮
					vertx.fileSystem().writeFile(jsonPath, Buffer.buffer(view.toString()), result ->{
						if (result.succeeded()) {
							logger.info("写入文件"+jsonPath);
					    } else {
							logger.warning("写入文件异常" + result.cause());
					    }
					});
					p.onNext(new JsonObject().put("VIEW", view));
					p.onCompleted();
				}
			}
		});
		return ob;
	}
	
	public Observable<JsonObject> readMeta(LocalMap<String, Object> metaMap, JsonObject param) {
		Observable<JsonObject> ob = Observable.create(p -> {
			if (metaMap.keySet().contains(param.getString("META"))) {
				p.onNext(new JsonObject().put("META", metaMap.get(param.getString("META"))));
				p.onCompleted();
			}
		});
		return ob;
	}

	public Observable<JsonObject> readData(JsonObject param) {
		Observable<JsonObject> ob = Observable.create(p -> {
			
			String sql = "select * from hotel limit 3";
			vertx.eventBus().send("db.query", sql, message -> {
				p.onNext(new JsonObject().put("DATA", (JsonObject) message.result().body()));
				p.onCompleted();
			});
		});
		return ob;
	}

	public Observable<JsonObject> readDict(String dict) {
		// TODO 读取缓存
		// 缓存没有读取json文件
		Observable<JsonObject> ob = Observable.create(d -> {
			vertx.fileSystem().readFile("doc/SY_SERV_DICT/" + dict + ".json", result -> {
				if (result.succeeded()) {
					d.onNext(new JsonObject().put("DICT", new JsonObject(result.result().toString())));
					d.onCompleted();
				} else {
					logger.warning(result.cause().getLocalizedMessage());
				}
			});
		});
		return ob;
	}
}
