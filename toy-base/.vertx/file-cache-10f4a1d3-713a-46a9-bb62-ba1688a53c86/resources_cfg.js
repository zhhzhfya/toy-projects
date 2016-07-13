{
	"jdbc_config": {
		"desc": "平台数据库",
		"url": "jdbc:mysql://192.168.0.34:3306/gds",
		"driver_class": "com.mysql.jdbc.Driver",
		"user": "root",
		"password": "imike@2015",
		"max_pool_size": 10,
		"test": "select * from orders order by id limit 3"
	},
	"redis_config": {
		"host": "192.168.0.21",
		"port": 6379
	},
	"mongodb_config": {
		"host": "192.168.0.21",
		"port": 6379
	},
	"web_config" : {
		"port" : 8081
	}
}