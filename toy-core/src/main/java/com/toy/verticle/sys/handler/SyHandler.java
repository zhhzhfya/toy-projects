package com.toy.verticle.sys.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import com.toy.verticle.sys.handler.impl.SyHandlerImpl;

public interface SyHandler extends Handler<RoutingContext> {

	static SyHandler create() {
		return new SyHandlerImpl();
	}

}