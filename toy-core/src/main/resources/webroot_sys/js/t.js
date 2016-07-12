var eb = new EventBus("/eventbus/");
eb.onopen = function() {
	// 监听系统信息频道
	eb.registerHandler("system.timer", function(err, msg) {
		if ($('#sysinfo')) {
			$('#sysinfo').text("服务器性能参数：" + msg.body);
		}
	});
};