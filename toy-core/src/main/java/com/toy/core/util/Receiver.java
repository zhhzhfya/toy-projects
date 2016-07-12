package com.toy.core.util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * 
 * @author zzy
 *
 */
public class Receiver extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		Runner.runClustered(Receiver.class);
	}

	@Override
	public void start() throws Exception {

		EventBus eb = vertx.eventBus();
		
		eb.consumer("system.timer", message -> {
			System.out.println("Received message: " + message.body());
			// Now send back reply
				//message.reply("pong!");
			});
		System.out.println("Receiver ready!");
	}
}
