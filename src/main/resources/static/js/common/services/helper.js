define(['angular'], function (angular) {
    'use strict';

    var mod = angular.module('common.helper', []);
    mod.service('helper', function () {
        return {
            sayHi: function () {
                return 'Welcome to use Spring Boot & Angular!';
            }
        };
    });

    return mod;
});
