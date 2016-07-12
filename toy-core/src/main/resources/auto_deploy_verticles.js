{
	"desc" : "系统的启动配置",
	"autoDeployVerticles" : [ {
		"desc" : "MetaWorker",
		"class" : "com.toy.verticle.MetaVerticle"
	}, {
		"desc" : "WebWorker",
		"class" : "com.toy.verticle.WebWorker",
		"config" : {
			"port" : 8081
		}
	}, {
		"desc" : "verticle管理",
		"class" : "com.toy.verticle.VerticleWorker"
	}, {
		"desc" : "jdbc管理",
		"class" : "com.toy.verticle.JdbcWorker",
		"config" : {
			"desc" : "平台数据库",
			"url" : "jdbc:mysql://192.168.0.34:3306/gds",
			"driver_class" : "com.mysql.jdbc.Driver",
			"user" : "root",
			"password" : "imike@2015",
			"max_pool_size" : 10,
			"test" : "select * from orders order by id limit 3"
		}
	}, {
		"desc" : "redis管理",
		"class" : "com.toy.verticle.RedisWorker",
		"config" : {
			"host" : "192.168.0.21",
			"port" : 6379
		}
	}, {
		"desc" : "TimerVerticle",
		"class" : "com.toy.verticle.TimerVerticle"
	}, {
		"desc" : "SysWorker",
		"class" : "com.toy.verticle.SysWorker",
		"config" : {}
	}, {
		"desc" : "RespontoryWorker",
		"class" : "com.toy.verticle.RespontoryWorker",
		"config" : {}
	} ]
}