var eb = new EventBus("/eventbus/");
eb.onopen = function() {
	// 监听系统信息频道
	eb.registerHandler("system.timer", function(err, msg) {
		if ($('#sysinfo')) {
			$('#sysinfo').text("服务器性能参数：" + msg.body);
		}
	});
};
function addTabPanel(containerid, config) {
	var result = new EJS({
		url : 'templates/web/tab.ejs'
	}).render(config);
	$('#' + containerid).tabs('add', {
		title : config['title'],
		content : result,
		closable : config['closable'] || false
	});
}
function loadTemplate(config) {
	var tmpl = config['tmpl'];
	var containerid = config['containerid'];
	var config = config['config'];
	var result = new EJS({
		url : 'templates/web/' + tmpl + '.ejs'
	}).render(config);
	if (tmpl == 'tab') {
		addTabPanel(containerid, config);
	} else {
		$("#" + containerid).html(result);
	}
	$.parser.parse($('#' + containerid));
}
$(function() {
	// var desktopConfig = {
	// modelCode : "desktop",
	// config : {
	// north : {},
	// center : {},
	// south : {}
	// }
	// };
	// var config = {
	// tmpl : "layout",
	// containerid : "desktop_layout",
	// config : desktopConfig
	// };
	// loadTemplate(config);
	// config = {
	// tmpl : "menu",
	// containerid : "body_n",
	// config : {}
	// };
	// loadTemplate(config);
	config = {
		tmpl : "tabs",
		containerid : "body_c",
		config : {
			modelCode : "desktop"
		}
	};
	var sysinfo = '<div id="sysinfo" class="easyui-panel" style="padding: 5px;"></div>'
	$("#body_s").html(sysinfo);
	// $('#desktoptabs').tabs({
	// width : $("#desktoptabs").parent().width(),
	// height : "auto"
	// });

	// $('#desktopcenter').tabs({
	// width : $("#desktopcenter").parent().width(),
	// height : "auto"
	// });
	loadTemplate(config);
	config = {
		tmpl : "tab",
		containerid : "desktoptabs",
		config : {
			modelCode : "service1",
			title : "首页",
			closable : false
		}
	};
	loadTemplate(config);
	config = {
		tmpl : "tab",
		containerid : "desktoptabs",
		config : {
			modelCode : "service1",
			title : "adsfasdfasdf",
			closable : true
		}
	};
	loadTemplate(config);
});

function menuHandler() {
	// alert(event);
	config = {
		tmpl : "tab",
		containerid : "desktoptabs",
		config : {
			modelCode : "service1",
			title : "service1"
		}
	};
	loadTemplate(config);
}
