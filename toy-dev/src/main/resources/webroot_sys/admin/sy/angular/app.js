angular.module('main', [ 'ui.router', 'oc.lazyLoad', 'knalli.angular-vertxbus' ])
.constant('Settings', {
	Context : {
		path : 'http://localhost:8080'
	}
})
.config(
		[ '$locationProvider', '$urlRouterProvider', '$stateProvider', 'vertxEventBusProvider', 'Settings', '$httpProvider',
				function($locationProvider, $urlRouterProvider, $stateProvider, vertxEventBusProvider, Settings, $httpProvider) {
					// $urlRouterProvider.otherwise('/desktop');
					$stateProvider.state('desktop', {
						url : '/desktop',
						views : {
							'header' : {
								templateUrl : '/admin/pages/header.html'
							},
							'content' : {
								templateUrl : '/admin/sy/desktop.html'
							}
						},
						params : {
							'servid' : null
						},
						resolve : {
							loadMyCtrl : [ '$ocLazyLoad', function($ocLazyLoad) {
								return $ocLazyLoad.load([ '/admin/sy/js/desktopctrl.js' ]);
							} ]
						}
					}).state('formlist', {
						url : '/formlist',
						views : {
							'header' : {
								templateUrl : '/admin/pages/header.html'
							},
							'content' : {
								templateUrl : '/admin/sy/formlist.html'
							}
						},
						params : {
							'servid' : null
						},
						resolve : {
							loadMyCtrl : [ '$ocLazyLoad', function($ocLazyLoad) {
								return $ocLazyLoad.load([ '/admin/sy/js/formlistdata.js', '/admin/sy/js/formlistctrl.js' ]);
							} ]
						}
					}).state('formcard', {
						url : '/formcard',
						views : {
							'header' : {
								templateUrl : '/admin/pages/header.html'
							},
							'content' : {
								templateUrl : '/admin/sy/formcard.html'
							}
						},
						params : {
							'servid' : null,
							'data' : null,
							'pkcode' : null
						},
						resolve : {
							loadMyCtrl : [ '$ocLazyLoad', function($ocLazyLoad) {
								console.log("card lazy load");
								return $ocLazyLoad.load([ '/admin/sy/js/formcard_serv.js', '/admin/sy/js/formcardctrl.js' ]);
							} ]
						}
					});

					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
					vertxEventBusProvider.enable().useReconnect().useUrlServer(Settings.Context.path);
					
					console.log("load");
				} ]);