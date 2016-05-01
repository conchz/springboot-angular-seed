define(['angular', './HomeCtrl', 'common'], function (angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', ['lavenderx.common']);
    mod.config(['$routeProvider', '$locationProvider',
        function ($routeProvider, $locationProvider) {
            $locationProvider.html5Mode({
                enabled: true
            });

            $routeProvider
                .when('/', {templateUrl: '/static/views/home.html', controller: controllers.HomeCtrl})
                .otherwise({templateUrl: '/notFound'});
        }]);

    return mod;
});
