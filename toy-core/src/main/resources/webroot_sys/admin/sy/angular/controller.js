angular.module("main" ).controller("homeCtrl", ['$scope', '$state', function($scope, $state) {
	$state.go('desktop', {
		servid : 'desktop'
	});
	$scope.designObject = function(name) {
		$state.go('formlist', {
			servid : 'object'
		});
	};

	$scope.designObjectLinks = function(name) {
		$state.go('formcard', {
			servid : 'objectlinks'
		});
	};

	$scope.designServices = function(name) {
		$state.go('desktop', {
			servid : 'service'
		});
	};

	$scope.designDicts = function(name) {
		$state.go('formlist', {
			servid : 'dict'
		});
	};
	$scope.query = function(name) {
		$state.go('formlist', {
			servid : name
		});
	};
	
} ]);