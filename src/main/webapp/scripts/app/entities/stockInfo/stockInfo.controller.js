'use strict';

angular.module('cabzzaApp')
    .controller('StockInfoController', function ($scope, StockInfo, StockInfoSearch) {
        $scope.stockInfos = [];
        $scope.loadAll = function() {
            StockInfo.query(function(result) {
               $scope.stockInfos = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            StockInfo.get({id: id}, function(result) {
                $scope.stockInfo = result;
                $('#deleteStockInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StockInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStockInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StockInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stockInfos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.stockInfo = {
                name: null,
                symbol: null,
                quotesStartDate: null,
                quotesEndDate: null,
                isInvestorModeAvaiable: null,
                id: null
            };
        };
    });
