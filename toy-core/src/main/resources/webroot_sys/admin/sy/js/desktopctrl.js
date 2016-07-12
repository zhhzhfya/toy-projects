angular.module('main').controller('desktopctrl', ['$scope', function($scope) {
	console.log('desktopctrl load');
	return;
//	var numbers = [];
//	for (var i = 0; i < 100; i++) {
//		numbers.push(1);
//	}
//	$scope.numbers = numbers;
//	var d = 1
//	$scope.go = function() {
//		d++;
//		$scope.numbers = $scope.numbers.map(function() {
//			return d;
//		});
//	}
//	$scope.sysinfo = "";
//	var eb = new EventBus("/eventbus/");
//	eb.onopen = function() {
//		// 监听系统信息频道
//		eb.registerHandler("system.timer", function(err, msg) {
//			$scope.sysinfo = msg.body;
//			$scope.$apply();
//		});
//	};
	
}] );