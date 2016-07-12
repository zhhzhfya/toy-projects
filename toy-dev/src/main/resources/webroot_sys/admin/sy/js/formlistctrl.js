'use strict';
angular.module("main").controller("formlistctrl",
		[ '$scope', '$state', '$stateParams', 'vertxEventBusService', '$http', '$timeout', function($scope, $state, $stateParams, vertxEventBusService, $http, $timeout) {
			// 服务id
			var servid = $stateParams.servid;
			// 查询这个服务的数据
			vertxEventBusService.on('system.timer', function(err, message) {
				// console.log('Received a message: ', message, err);
			});
			var data = [], loadData = function() {
				// 请求SY_SERV的元数据、能量数据
				$http.post("/sy/base/query", {
					"META" : "SY_SERV"
				}, {
					timeout : 10000
				}).success(function(result) {
					var view = result['VIEW'];
					$scope._cols_ = view['_COLS_'];		
					console.log(result);
				}).error(function(error) {
					console.log(error);
				});

			};// _formlistdata;

			$scope._cols_ = {};
			$scope._data_ = data._DATA_;
			console.log($scope._cols_);
			$scope.goObjectLink = function() {
				$state.go('formcard', {
					servid : 'objectlinks'
				});

			};
			$scope.rowClick = function(row) {
				$state.go('formcard', {
					servid : 'objectlinks',
					pkcode : row['SERV_ID'],
					data : row
				});
			};
			$timeout(function() {
				loadData();
			}, 0);
		} ]);