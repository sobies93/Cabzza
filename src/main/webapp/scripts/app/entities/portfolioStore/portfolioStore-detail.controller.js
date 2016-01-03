'use strict';

angular.module('cabzzaApp')
    .controller('PortfolioStoreDetailController', function ($scope, $rootScope, $stateParams, entity, PortfolioStore, NewStockWallet, StockInfo) {
        $scope.portfolioStore = entity;
        $scope.load = function (id) {
            PortfolioStore.get({id: id}, function(result) {
                $scope.portfolioStore = result;
            });
        };
        var unsubscribe = $rootScope.$on('cabzzaApp:portfolioStoreUpdate', function(event, result) {
            $scope.portfolioStore = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
