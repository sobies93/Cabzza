'use strict';

angular.module('cabzzaApp')
    .controller('StockWalletDetailController', function ($scope, $rootScope, $stateParams, entity, StockWallet, User) {
        $scope.stockWallet = entity;
        $scope.load = function (id) {
            StockWallet.get({id: id}, function(result) {
                $scope.stockWallet = result;
            });
        };
        var unsubscribe = $rootScope.$on('cabzzaApp:stockWalletUpdate', function(event, result) {
            $scope.stockWallet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
