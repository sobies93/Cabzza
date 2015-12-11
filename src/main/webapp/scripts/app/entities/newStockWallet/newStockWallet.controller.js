'use strict';

angular.module('cabzzaApp')
    .controller('NewStockWalletController', function ($scope, NewStockWallet, NewStockWalletSearch) {
        $scope.newStockWallets = [];
        $scope.loadAll = function() {
            NewStockWallet.query(function(result) {
               $scope.newStockWallets = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            NewStockWallet.get({id: id}, function(result) {
                $scope.newStockWallet = result;
                $('#deleteNewStockWalletConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            NewStockWallet.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNewStockWalletConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            NewStockWalletSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.newStockWallets = result;
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
            $scope.newStockWallet = {
                name: null,
                historicalDataDate: null,
                calculatingsDate: null,
                prognoseDate: null,
                riskfreeRate: null,
                expectedReturn: null,
                expectedVariation: null,
                sharpRatio: null,
                id: null
            };
        };
    });
