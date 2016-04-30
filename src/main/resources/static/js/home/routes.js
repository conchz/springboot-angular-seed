define(['angular', './HomeCtrl', 'common'], function (angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', ['lavenderx.common']);
    mod.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: '/static/views/home.html', controller: controllers.HomeCtrl})
            .otherwise({templateUrl: '/static/views/404.html'});
    }]);

    return mod;
});
