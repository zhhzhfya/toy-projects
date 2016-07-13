package com.toy.respontory.verticle;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.rxjava.ext.auth.User;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import io.vertx.rxjava.ext.sql.SQLConnection;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscriber;

public class JdbcWorker extends io.vertx.rxjava.core.AbstractVerticle {

	private static Logger logger = Logger.getLogger(JdbcWorker.class.getName());

	public JDBCClient client;

	private JsonObject config;

	public JdbcWorker() {
	}

	@Override
	public void start() throws Exception {
		JsonObject config = vertx.getOrCreateContext().config();
		JsonObject jdbcCfg = null;
		for (String key : config.getMap().keySet()) {
			if (key.startsWith("jdbc")) {
				jdbcCfg = config.getJsonObject(key);
			}
		}
		if (jdbcCfg == null) {
			logger.warning("没有找到jdbc的配置文件");
			return;
		}
		String test = config.getString("test");
//		config = new JsonObject().put("url", "jdbc:hsqldb:mem:test?shutdown=true")
//		        .put("driver_class", "org.hsqldb.jdbcDriver");
		client = JDBCClient.createShared(vertx, jdbcCfg);
		
//		client.getConnectionObservable().subscribe(
//	        conn -> {
//
//	          // Now chain some statements using flatmap composition
//	        	Observable<ResultSet> resa = conn.updateObservable("CREATE TABLE test(col VARCHAR(20),col2 VARCHAR(20),col3 VARCHAR(20),col4 VARCHAR(20),col5 VARCHAR(20),col6 VARCHAR(20),col7 VARCHAR(20),col8 VARCHAR(20))").
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.updateObservable("INSERT INTO test (col, col2, col3, col4, col5, col6, col7, col8) VALUES ('val1','val1','val1','val1','val1','val1','val1','val1')")).
//	                    flatMap(result -> conn.queryObservable("SELECT * FROM test"));
//
//	          // Subscribe to the final result
//	          resa.subscribe(resultSet -> {
//	            System.out.println("Results : " + resultSet.getRows());
//	          }, err -> {
//	            System.out.println("Database problem");
//	            err.printStackTrace();
//	          }, conn::close);
//	        },
//
//	        // Could not connect
//	        err -> {
//	          err.printStackTrace();
//	        }
//	    );
//		
//		query("SELECT * FROM test").subscribe(data -> {
//			JsonObject json = data;
//			JsonArray columns = (JsonArray) json.getValue("columns");
//			JsonArray js = (JsonArray) json.getValue("data");
//			for (Object j : js) {
//				System.out.println("-------->" + j);
//			}
//		});
		
		vertx.eventBus().consumer("db.query", message -> {
			String sql = (String) message.body();
			runSql(sql).subscribe(data -> {
				message.reply(data);
			});
		});
		
	}

	private Observable<JsonObject> runSql(String sql) {

		return Observable.create(subscriber -> {
			client.getConnectionObservable().subscribe(conn -> {
				conn.queryObservable(sql).subscribe(result -> {
					List<JsonArray> js = result.getResults();
					JsonObject data = new JsonObject();
					data.put("columns", result.getColumnNames());
					data.put("data", js);
					subscriber.onNext(data);
					subscriber.onCompleted();
				}, err -> {
					subscriber.onError(new Throwable(err.getLocalizedMessage()));
					err.printStackTrace();
				}, conn::close);
			});
		});
	}

	private static <T> Observable<T> makeObservable(final Callable<T> func) {
		return Observable.create(new Observable.OnSubscribe<T>() {
			@Override
			public void call(Subscriber<? super T> subscriber) {
				try {
					subscriber.onNext(func.call());
				} catch (Exception ex) {
					logger.log(Level.WARNING, "Error reading from the database", ex);
				}
			}
		});
	}

}
