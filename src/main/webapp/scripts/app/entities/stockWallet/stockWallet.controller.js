'use strict';

angular.module('cabzzaApp')
    .controller('StockWalletController', function ($scope, StockWallet, StockWalletSearch, ParseLinks) {
        $scope.stockWallets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            StockWallet.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.stockWallets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            StockWallet.get({id: id}, function(result) {
                $scope.stockWallet = result;
                $('#deleteStockWalletConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            StockWallet.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStockWalletConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StockWalletSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stockWallets = result;
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
            $scope.stockWallet = {
                quoteSymbol: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });
