'use strict';

angular.module('cabzzaApp')
		.controller('ProjectListController', function ($scope, $state, $rootScope, UsersNewStockWallet) {

            $scope.stockWallets = [];

            $scope.loadAll = function() {
                UsersNewStockWallet.query(function(result) {
                   $scope.stockWallets = result;
                });
            };
            $scope.loadAll();

		});
