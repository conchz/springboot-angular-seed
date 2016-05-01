define(['angular', './HomeCtrl', 'common'], function (angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', ['lavenderx.common']);
    mod.config(['$routeProvider', '$locationProvider', '$httpProvider',
        function ($routeProvider, $locationProvider, $httpProvider) {
            $locationProvider.html5Mode({
                enabled: true
            });

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

            $routeProvider
                .when('/', {templateUrl: '/static/views/home.html', controller: controllers.HomeCtrl})
                .otherwise({templateUrl: '/notFound'});
        }]);

    return mod;
});
