'use strict';

angular.module('cabzzaApp').controller('PortfolioStoreDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PortfolioStore', 'NewStockWallet', 'StockInfo',
        function($scope, $stateParams, $modalInstance, entity, PortfolioStore, NewStockWallet, StockInfo) {

        $scope.portfolioStore = entity;
        $scope.newstockwallets = NewStockWallet.query();
        $scope.stockinfos = StockInfo.query();
        $scope.load = function(id) {
            PortfolioStore.get({id : id}, function(result) {
                $scope.portfolioStore = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('cabzzaApp:portfolioStoreUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.portfolioStore.id != null) {
                PortfolioStore.update($scope.portfolioStore, onSaveFinished);
            } else {
                PortfolioStore.save($scope.portfolioStore, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
