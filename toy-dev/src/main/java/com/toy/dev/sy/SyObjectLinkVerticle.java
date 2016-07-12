package com.toy.dev.sy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;

import com.toy.dev.utils.MetaConstant;
import com.toy.dev.utils.MetaFileUtils;

public class SyObjectLinkVerticle extends AbstractVerticle {
	private Logger logger = Logger.getLogger(SyObjectLinkVerticle.class.getName());
	public SyObjectLinkVerticle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		// 读取元定义
		Buffer config = vertx.fileSystem().readFileBlocking("meta/sy_object_link.xml");
		JsonObject metaData = MetaFileUtils.readMeteData(config.toString());
		vertx.eventBus().send(MetaConstant.REG_META, metaData, (message -> {
			logger.info(message.result().body().toString());
		}));
	}
}
