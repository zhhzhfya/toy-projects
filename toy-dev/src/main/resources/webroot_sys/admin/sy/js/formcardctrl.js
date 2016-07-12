angular.module("main").controller("formcardctrl", [ '$scope', '$state', '$stateParams', '$window', function($scope, $state, $stateParams, $window) {
	var servid = $stateParams.servid;
	var pkcode = $stateParams.pkcode;
	var data = $stateParams.data;
	console.log(pkcode);
	console.log(data);
	/*
	 * 查obj、link、serv、act:event
	 * */
	// 根据服务id->找：服务定义、视图定义、对象关系、对象（【本地找、远程找】根据$PK_CODE$找到对象的单条、多条数据）
	/*
	 * //数据展示： 服务定义、 视图定义（ 视图模板、 包含元素（ 对象、 关系、 按钮）、 字段定义（ 类型、 排序）） 对象定义 ===
	 * 》生成模板？ { "SERVS": { "SERV_CODE": "WO_ORDER_MGR", "SERV_NAME": "订单管理服务",
	 * "APP": "MK_ORDER_APP", "DEPLOY_RULE": { "REGION": "BJ,SH,SC", "MACHINE":
	 * "MACHINE_A,MACHINE_B", "IP": "100.23.4.22,100.23.4.23,100.23.4.24" },
	 * "VERSION": { "VERSION": "1.0.3", "STATUS": "DEV/TEST/PRODUCT",
	 * "CREATE_TIME": "2016-04-12" }, "VIEWS": { "TEMPLATE": { "ID": "", "NAME":
	 * "", "FORM_LIST": "default", "FORM_CARD": "default" } "OBJLINKS": {
	 * "LINK": { "MAIN_OBJECT": { "OBJECT": "WO_ORDER", "NAME": "订单",
	 * "MAIN_VIEW": true }, "SUB_LINKS"： [{ "MAIN_OBJ": "WO_ORDER", "SUB_OBJ":
	 * "WO_ORDER_LOG", "LINK_FIELDS": [{ "MAIN_FIELD": "WO_CODE", "SUB_FILELD":
	 * "WO_CODE", "LINK_OPT": "=" }, { "MAIN_OBJ": "WO_ORDER", "SUB_OBJ":
	 * "WO_ORDER_SORT", "LINK_FIELDS": [{ "MAIN_FIELD": "WO_CODE", "SUB_FILELD":
	 * "WO_CODE", "LINK_OPT": "=" }] }] }] } } } }
	 */

	// 本地sqlite查找服务定义、视图定义、对象关系、对象定义。【用于页面展示】
	// 根据对象关系查找关联的数据
	var serv_def = __serv_def;
	$scope.items = serv_def.ITEMS;
	$scope.colwidth = 12 / 3;
	console.log(servid);

	$scope.goBack = function(name) {
		$state.go('formlist', {
			servid : 'objectlinks'
		});
	};

	var store = Lawnchair({
		name : 'table1'
	}, function(store) {
	});
	store.save({
		key : 'hust',
		name : 'xesam_1'
	});
	store.save({
		key : 'whu',
		name : 'xesam_2'
	});
} ]);