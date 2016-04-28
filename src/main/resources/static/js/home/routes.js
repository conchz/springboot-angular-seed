define(['angular', './HomeCtrl', 'common'], function (angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', ['lavenderx.common']);
    mod.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: '/static/js/home/home.html', controller: controllers.HomeCtrl})
            .otherwise({templateUrl: '/templates/404.html'});
    }]);

    return mod;
});
