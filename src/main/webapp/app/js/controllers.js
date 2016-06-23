'use strict';

angular.module('CrmDemo.controllers',[]).controller('CustomerDetailCtrl',['$scope','$location', '$routeParams','Customer','Company', function ($scope, $location, $routeParams, Customer, Company) {
	$scope.companies = Company.query();
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
    	if (!!$scope.customer.id && $scope.customer.company !== undefined) {
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
}]).controller('CompanyDetailCtrl',['$scope','$location', '$routeParams','Company', function ($scope, $location, $routeParams, Company) {
	if ($location.path() === "/company/new") {
        $scope.company = {};
    } else {
        $scope.company = Company.get({
            id : $routeParams.companyId
        });
    }

    $scope.save = function() {
        if (!!$scope.company.id) {
            Company.update($scope.company, function(company) {
                $location.path('/company/list');
            });
        } else {
            Company.save($scope.company, function(company) {
                $location.path('/company/list');
            });
        }
    };
}]).controller('ProductCategoryDetailCtrl',['$scope','$location', '$routeParams','ProductCategory', function ($scope, $location, $routeParams, ProductCategory) {
	if ($location.path() === "/category/new") {
        $scope.category = {};
    } else {
        $scope.category = ProductCategory.get({
            id : $routeParams.categoryId
        });
    }

    $scope.save = function() {
        if (!!$scope.category.id) {
        	ProductCategory.update($scope.category, function(category) {
                $location.path('/category/list');
            });
        } else {
        	ProductCategory.save($scope.category, function(category) {
                $location.path('/category/list');
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

}]).controller('ProductCategoryListCtrl',['$scope','ProductCategory', function ($scope, ProductCategory) {

    $scope.categories = ProductCategory.query();
    $scope.filteredResults = false;

    $scope.search = function() {
        $scope.categories = ProductCategory.query(
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
        $scope.categories = ProductCategory.query();
    };

}]).controller('CompanyListCtrl',['$scope','Company', function ($scope, Company) {

    $scope.companies = Company.query();
    $scope.filteredResults = false;

    $scope.search = function() {
        $scope.companies = Company.query(
            {
                searchString: $scope.searchString
            },
            function() {
                $scope.filteredResults = true;
            }
        );
    };
    
    $scope.deleteCompany = function(companyId) {
        Company.delete(
            {
                id: companyId
            },
            function() {
                if(!!$scope.searchString && !!$scope.filteredResults) {
                    $scope.search();
                } else {
                    $scope.companies = Company.query();
                }
            });
    };

    $scope.clearSearch = function() {
        $scope.searchString = null;
        $scope.filteredResults = false;
        $scope.companies = Company.query();
    };

}]);
