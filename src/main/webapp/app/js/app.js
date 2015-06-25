'use strict';


// Declare app level module which depends on filters, services and directives
angular.module('CrmDemo', ['CrmDemo.controllers', 'CrmDemo.filters', 'CrmDemo.services', 'CrmDemo.directives', 'ngRoute','ngResource' ])
        .config([ '$routeProvider', function($routeProvider) {
            $routeProvider.when('/customer/list', {
                templateUrl : 'app/partials/customer-list.html',
                controller : 'CustomerListCtrl'
            });
            $routeProvider.when('/customer/edit/:customerId', {
                templateUrl : 'app/partials/customer-detail.html',
                controller : 'CustomerDetailCtrl'
            });
            $routeProvider.when('/customer/new', {
                templateUrl : 'app/partials/customer-detail.html',
                controller : 'CustomerDetailCtrl'
            });
            $routeProvider.otherwise({
                redirectTo : '/customer/list'
            });
        } ]);
