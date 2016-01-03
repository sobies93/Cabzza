'use strict';

angular.module('cabzzaApp')
    .controller('PortfolioStoreController', function ($scope, PortfolioStore, PortfolioStoreSearch) {
        $scope.portfolioStores = [];
        $scope.loadAll = function() {
            PortfolioStore.query(function(result) {
               $scope.portfolioStores = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PortfolioStore.get({id: id}, function(result) {
                $scope.portfolioStore = result;
                $('#deletePortfolioStoreConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PortfolioStore.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePortfolioStoreConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PortfolioStoreSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.portfolioStores = result;
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
            $scope.portfolioStore = {
                percent: null,
                id: null
            };
        };
    });
