'use strict';

angular.module('cabzzaApp')
    .controller('StockQuotesController', function ($scope, StockQuotes, StockQuotesSearch) {
        $scope.stockQuotess = [];
        $scope.loadAll = function() {
            StockQuotes.query(function(result) {
               $scope.stockQuotess = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            StockQuotes.get({id: id}, function(result) {
                $scope.stockQuotes = result;
                $('#deleteStockQuotesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StockQuotes.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStockQuotesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StockQuotesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stockQuotess = result;
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
            $scope.stockQuotes = {
                date: null,
                value: null,
                splitRate: null,
                dividend: null,
                id: null
            };
        };
    });
