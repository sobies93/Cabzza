'use strict';

angular.module('cabzzaApp')
		.controller('MainController', function ($scope, Principal, $state) {
			Principal.identity().then(function (account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
			});
		});
