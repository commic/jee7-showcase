'use strict';

angular.module('CrmDemo.controllers',[]).controller('CustomerDetailCtrl',['$scope','$location', '$routeParams','Customer', function ($scope, $location, $routeParams, Customer) {

    if ($location.path() === "/customer/new") {
        $scope.customer = {
            createDate : new Date(),
            sex : 'unknown'
        };
    } else {
        $scope.customer = Customer.get({
            id : $routeParams.customerId
        });
    }

    $scope.save = function() {
        if (!!$scope.customer.id) {
            Customer.update($scope.customer, function(customer) {
                $location.path('/customer/list');
            });
        } else {
            Customer.save($scope.customer, function(customer) {
                $location.path('/customer/list');
            });
        }
    };
}]).controller('ProductDetailCtrl',['$scope','$location', '$routeParams','Product','ProductCategory', function ($scope, $location, $routeParams, Product, ProductCategory) {
	$scope.productCategories = ProductCategory.query();
    if ($location.path() === "/product/new") {
        $scope.product = {};
    } else {
        $scope.product = Product.get({
            id : $routeParams.productId
        });
    }

    $scope.save = function() {
        if (!!$scope.product.id) {
            Product.update($scope.product, function(product) {
                $location.path('/product/list');
            });
        } else {
            Product.save($scope.product, function(product) {
                $location.path('/product/list');
            });
        }
    };
}]).controller('CustomerListCtrl',['$scope','Customer', function ($scope, Customer) {

    $scope.customers = Customer.query();
    $scope.filteredResults = false;
    $scope.result = "";

    $scope.search = function() {
        $scope.customers = Customer.query(
            {
                searchString: $scope.searchString
            },
            function() {
                $scope.filteredResults = true;
            }
        );
    };

    $scope.clearSearch = function() {
        $scope.searchString = null;
        $scope.filteredResults = false;
        $scope.customers = Customer.query();
    };

    $scope.deleteCustomer = function(customerId) {
        Customer.delete(
            {
                id: customerId
            },
            function() {
                if(!!$scope.searchString && !!$scope.filteredResults) {
                    $scope.search();
                } else {
                    $scope.customers = Customer.query();
                }
            });
    };
    $scope.sendMail = function(customerId) {
        Customer.sendMail(
            {
                id: customerId
            },
            function() {
                $scope.result = "Mail sent";
            });
    }

}]).controller('ProductListCtrl',['$scope','Product', function ($scope, Product) {

    $scope.products = Product.query();
    $scope.filteredResults = false;

    $scope.search = function() {
        $scope.products = Product.query(
            {
                searchString: $scope.searchString
            },
            function() {
                $scope.filteredResults = true;
            }
        );
    };

    $scope.clearSearch = function() {
        $scope.searchString = null;
        $scope.filteredResults = false;
        $scope.products = Product.query();
    };

    $scope.deleteProduct = function(productId) {
        Product.delete(
            {
                id: productId
            },
            function() {
                if(!!$scope.searchString && !!$scope.filteredResults) {
                    $scope.search();
                } else {
                    $scope.products = Product.query();
                }
            });
    };

}]);
