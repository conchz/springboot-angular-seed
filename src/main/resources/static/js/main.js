(function (requirejs) {
    'use strict';

    // -- RequireJS config --
    requirejs.config({
        // Packages = top-level folders; loads a contained file named 'main.js"
        packages: ['common', 'home'],
        shim: {
            // Hopefully this all will not be necessary but can be fetched from WebJars in the future
            'angular': {
                deps: ['jquery'],
                exports: 'angular'
            },
            'bootstrap': ['jquery'],
            'angular-route': ['angular'],
            'angular-cookies': ['angular'],
            'angular-ui-bootstrap': ['angular'],
            'angular-ui-bootstrap-tpls': ['angular'],
            'angular-ui-grid': ['angular']
        },
        paths: {
            'requirejs': ['../lib/requirejs/require'],
            'jquery': ['../lib/jquery/jquery'],
            'bootstrap': ['../lib/bootstrap/js/bootstrap'],
            'angular': ['../lib/angularjs/angular'],
            'angular-route': ['../lib/angularjs/angular-route'],
            'angular-cookies': ['../lib/angularjs/angular-cookies'],
            'angular-ui-bootstrap': ['../lib/angular-ui-bootstrap/ui-bootstrap'],
            'angular-ui-bootstrap-tpls': ['../lib/angular-ui-bootstrap/ui-bootstrap-tpls'],
            'angular-ui-grid': ['../lib/ui-grid/ui-grid']
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
            'angular-ui-bootstrap',
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
