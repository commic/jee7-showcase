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
            $routeProvider.when('/company/list', {
                templateUrl : 'app/partials/company-list.html',
                controller : 'CompanyListCtrl'
            });
            $routeProvider.when('/company/edit/:companyId', {
                templateUrl : 'app/partials/company-detail.html',
                controller : 'CompanyDetailCtrl'
            });
            $routeProvider.when('/company/new', {
                templateUrl : 'app/partials/company-detail.html',
                controller : 'CompanyDetailCtrl'
            });
            $routeProvider.otherwise({
                redirectTo : '/customer/list'
            });
            $routeProvider.when('/product/list', {
                templateUrl : 'app/partials/product-list.html',
                controller : 'ProductListCtrl'
            });
            $routeProvider.when('/product/edit/:productId', {
                templateUrl : 'app/partials/product-detail.html',
                controller : 'ProductDetailCtrl'
            });
            $routeProvider.when('/product/new', {
                templateUrl : 'app/partials/product-detail.html',
                controller : 'ProductDetailCtrl'
            });
            $routeProvider.when('/category/list', {
                templateUrl : 'app/partials/productcategory-list.html',
                controller : 'ProductCategoryListCtrl'
            });
            $routeProvider.when('/category/edit/:categoryId', {
                templateUrl : 'app/partials/productcategory-detail.html',
                controller : 'ProductCategoryDetailCtrl'
            });
            $routeProvider.when('/category/new', {
                templateUrl : 'app/partials/productcategory-detail.html',
                controller : 'ProductCategoryDetailCtrl'
            });
        } ]);
