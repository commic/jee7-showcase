'use strict';

var MODULE_NAME = "crm-demo";

/* Services */
angular.module('CrmDemo.services', [ 'ngResource' ]).value('Debouncer', {
	/**
	 * Debounce a function call, making it callable an arbitrary number of times
	 * before it is actually executed once.
	 * 
	 * @param {function()}
	 *            func The function to debounce.
	 * @param {number}
	 *            wait The debounce timeout.
	 * @return {function()} A function that can be called an arbitrary number of
	 *         times within the given time.
	 */
	debounce : function(func, wait) {
		var timer;
		return function() {
			var context = this, args = arguments;
			clearTimeout(timer);
			timer = setTimeout(function() {
				timer = null;
				func.apply(context, args);
			}, wait);
		};
	}
}).value('version', '0.1').factory('Customer', function($resource, $http) {
	var Customer = $resource(

	"http://localhost\\:8080/" + MODULE_NAME + "/rest/customer/:id", {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
	return Customer;
}).factory('Product', function($resource) {
	var Product = $resource(

	"http://localhost\\:8080/" + MODULE_NAME + "/rest/product/:id", {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});

	return Product;
}).factory('ProductCategory', function($resource) {
	var ProductCategory = $resource(

	"http://localhost\\:8080/" + MODULE_NAME + "/rest/category/:id", {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});

	return ProductCategory;
}).factory('Company', function($resource) {
	var Company = $resource(

	"http://localhost\\:8080/" + MODULE_NAME + "/rest/company/:id", {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});

	return Company;
});
