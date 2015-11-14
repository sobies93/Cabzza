'use strict';

angular.module('cabzzaApp')
		.controller('QuoteFormController', function ($scope, $http, $location, $filter, quoteService) {
			$scope.getQuotes = function () {
				var startDate = $filter('date')($scope.quote.startDate, "yyyy-MM-dd");
				var endDate = $filter('date')($scope.quote.endDate, "yyyy-MM-dd");
				var quoteServicePromise = quoteService.acquire(startDate, endDate, $scope.quote.quoteSymbol);
				quoteServicePromise.then(function (result) {  // this is only run after $http completes
					$location.path('/list');
				});
			};
		});
