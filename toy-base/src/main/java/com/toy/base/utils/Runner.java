package com.toy.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Runner {

	private static final String CORE_DIR = "toy-core";
	private static final String CORE_JAVA_DIR = CORE_DIR + "/src/main/java/";

	public static void runClustered(Class clazz) {
		run(CORE_JAVA_DIR, clazz, new VertxOptions().setClustered(true), null);
	}

	public static void runClustered(Class clazz, VertxOptions options) {
		run(CORE_JAVA_DIR, clazz, options.setClustered(true), null);
	}

	public static void run(Class clazz) {
		run(CORE_JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
	}

	public static void run(Class clazz, DeploymentOptions options) {
		run(CORE_JAVA_DIR, clazz, new VertxOptions().setClustered(false), options);
	}

	public static void run(String dir, Class clazz, VertxOptions options, DeploymentOptions deploymentOptions) {
		run(dir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options, deploymentOptions);
	}

	public static void run(String dir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		// Smart cwd detection

		// Based on the current directory (.) and the desired directory
		// (exampleDir), we try to compute the vertx.cwd
		// directory:
		try {
			// We need to use the canonical file. Without the file name is .
			File current = new File(".").getCanonicalFile();
			if (dir.startsWith(current.getName()) && !dir.equals(current.getName())) {
				dir = dir.substring(current.getName().length() + 1);
			}
		} catch (IOException e) {
			// Ignore it.
		}

		System.setProperty("vertx.cwd", dir);
		Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}

}
