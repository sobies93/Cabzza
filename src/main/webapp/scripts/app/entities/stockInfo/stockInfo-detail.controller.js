'use strict';

angular.module('cabzzaApp')
    .controller('StockInfoDetailController', function ($scope, $rootScope, $stateParams, entity, StockInfo, StockQuotes, PortfolioStore) {
        $scope.stockInfo = entity;
        $scope.load = function (id) {
            StockInfo.get({id: id}, function(result) {
                $scope.stockInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('cabzzaApp:stockInfoUpdate', function(event, result) {
            $scope.stockInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
