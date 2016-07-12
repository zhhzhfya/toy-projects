package com.toy.dev.verticle;

import io.vertx.core.AbstractVerticle;

import java.util.logging.Logger;

import com.toy.dev.sy.AppVerticle;
import com.toy.dev.sy.SyObjectLinkVerticle;
import com.toy.dev.sy.SyObjectVerticle;
import com.toy.dev.sy.SyServVerticle;

public class SysWorker extends AbstractVerticle {

	private Logger logger = Logger.getLogger(SysWorker.class.getName());

	public SysWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() throws Exception {
		// 读取元定义
		vertx.deployVerticle(AppVerticle.class.getName());
		vertx.deployVerticle(SyObjectVerticle.class.getName());
		vertx.deployVerticle(SyObjectLinkVerticle.class.getName());
		vertx.deployVerticle(SyServVerticle.class.getName());
	}

	@Override
	public void stop() throws Exception {

	}
	

}
