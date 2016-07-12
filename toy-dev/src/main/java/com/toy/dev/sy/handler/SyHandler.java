package com.toy.dev.sy.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import com.toy.dev.sy.handler.impl.SyHandlerImpl;

public interface SyHandler extends Handler<RoutingContext> {

	static SyHandler create() {
		return new SyHandlerImpl();
	}

}