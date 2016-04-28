define(['angular', './routes', './HomeCtrl'], function (angular, routes) {
    'use strict';

    return angular.module('lavenderx.home', [
        'ngRoute',
        'ui.bootstrap',
        'ui.bootstrap.tpls',
        'ui.grid',
        'ui.grid.pagination',
        'ui.grid.selection',
        'home.routes']);
});
