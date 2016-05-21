(function (requirejs) {
    'use strict';

    // -- RequireJS config --
    requirejs.config({
        // Packages = top-level folders; loads a contained file named 'main.js'
        packages: ['common', 'home'],
        paths: {
            'requirejs': ['../lib/requirejs/require.min'],
            'jquery': ['../lib/jquery/jquery.min'],
            'bootstrap': ['../lib/bootstrap/js/bootstrap.min'],
            'angular': ['../lib/angularjs/angular.min'],
            'angular-route': ['../lib/angularjs/angular-route.min'],
            'angular-cookies': ['../lib/angularjs/angular-cookies.min'],
            'angular-ui-bootstrap-tpls': ['../lib/angular-ui-bootstrap/ui-bootstrap-tpls.min'],
            'angular-ui-grid': ['../lib/ui-grid/ui-grid.min']
        },
        shim: {
            // Hopefully this all will not be necessary but can be fetched from WebJars in the future
            'angular': {
                deps: ['jquery'],
                exports: 'angular'
            },
            'bootstrap': ['jquery'],
            'angular-route': ['angular'],
            'angular-cookies': ['angular'],
            'angular-ui-bootstrap-tpls': ['angular'],
            'angular-ui-grid': ['angular']
        }
    });

    requirejs.onError = function (err) {
        console.log(err);
    };

    // Load the app. This is kept minimal so it doesn't need much updating.
    require([
            'angular',
            'angular-cookies',
            'angular-route',
            'angular-ui-bootstrap-tpls',
            'angular-ui-grid',
            'jquery',
            'bootstrap',
            './app'],
        function (angular) {
            angular.bootstrap(document, ['app']);
        }
    );
})(requirejs);
