'use strict';

angular.module('cabzzaApp')
		.factory("quoteService", function ($http) {
			var quotes = [];
			return {
				acquire: function (startDate, endDate, quoteSymbol) {
					var quote = {startDate: startDate, endDate: endDate, quoteSymbol: quoteSymbol};
					return $http({
						method: 'POST', url: '/quotes',
						data: $.param(quote),
						headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					}).success(function (response) {
						quotes = response;
					}).error(function () {
						//fail
					});
					return quotes;
				},
				get: function() {
					return quotes;
				}
			};
		});