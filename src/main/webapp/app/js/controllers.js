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

}]);
