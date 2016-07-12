angular.module('main.services', [])

/**
 * Http请求
 */
.service('Http', [ '$rootScope', '$http', '$q', 'Settings', function($rootScope, $http, $q, Settings) {
	return {
		post : function(url, params) {
			url = Settings.Context.path + url;
			var deferred = $q.defer();
			return $http.post(url, params, {
				timeout : 10000
			}).success(function(result) {
				deferred.resolve(result);
			}).error(function(error) {
				deferred.reject(error);
			});
			return deferred.promise;
		},
		get : function(url) {
			url = Settings.Context.path + url;
			var deferred = $q.defer();
			return $http.get(url).success(function(result) {
				deferred.resolve(result);
			}).error(function(error) {
				deferred.reject(error);
			});
			return deferred.promise;
		}
	};
} ]);