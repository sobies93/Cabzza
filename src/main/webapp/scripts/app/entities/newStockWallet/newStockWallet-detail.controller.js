'use strict';

angular.module('cabzzaApp')
    .controller('NewStockWalletDetailController', function ($scope, $rootScope, $stateParams, entity, NewStockWallet, User, PortfolioStore) {
        $scope.newStockWallet = entity;
        $scope.load = function (id) {
            NewStockWallet.get({id: id}, function(result) {
                $scope.newStockWallet = result;
            });
        };
        var unsubscribe = $rootScope.$on('cabzzaApp:newStockWalletUpdate', function(event, result) {
            $scope.newStockWallet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
