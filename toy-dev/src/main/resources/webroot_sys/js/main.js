var eb = new EventBus("/eventbus/");
eb.onopen = function() {
	// 监听聊天频道
	eb.registerHandler("chat.to.client", function(err, msg) {
		$('#chat').append(msg.body + "\n");
		$('#chat').scrollTop( $('#chat')[0].scrollHeight );
	});
	// 监听系统信息频道
	eb.registerHandler("system.timer", function(err, msg) {
		$('#sysinfo').text("服务器性能参数："+msg.body);
	});
};


function send(event) {
	if (event.keyCode == 13 || event.which == 13) {
		var message = $('#input').val();
		if (message.length > 0) {
			eb.publish("chat.to.server", message);
			$('#input').val("");
		}
	}
}