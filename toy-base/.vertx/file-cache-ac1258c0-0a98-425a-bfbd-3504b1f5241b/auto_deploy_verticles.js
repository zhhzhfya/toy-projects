{
	"desc" : "系统的启动配置",
	"autoDeployVerticles" : [ {
		"desc":"toy-respontory", 
		"class":"maven:com.toy:toy-respontory:1.0.0::main-verticle",
		"auto_deploy": true
	}, {
		"desc":"toy-dev", 
		"class":"maven:com.toy:toy-dev:1.0.0::main-verticle",
		"auto_deploy": true
	}]
}