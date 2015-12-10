'use strict';

angular.module('cabzzaApp')
    .controller('StockQuotesDetailController', function ($scope, $rootScope, $stateParams, entity, StockQuotes, StockInfo) {
        $scope.stockQuotes = entity;
        $scope.load = function (id) {
            StockQuotes.get({id: id}, function(result) {
                $scope.stockQuotes = result;
            });
        };
        var unsubscribe = $rootScope.$on('cabzzaApp:stockQuotesUpdate', function(event, result) {
            $scope.stockQuotes = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
