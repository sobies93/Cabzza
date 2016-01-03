'use strict';

angular.module('cabzzaApp').controller('StockInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StockInfo', 'StockQuotes', 'PortfolioStore',
        function($scope, $stateParams, $modalInstance, entity, StockInfo, StockQuotes, PortfolioStore) {

        $scope.stockInfo = entity;
        $scope.stockquotess = StockQuotes.query();
        $scope.portfoliostores = PortfolioStore.query();
        $scope.load = function(id) {
            StockInfo.get({id : id}, function(result) {
                $scope.stockInfo = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('cabzzaApp:stockInfoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stockInfo.id != null) {
                StockInfo.update($scope.stockInfo, onSaveFinished);
            } else {
                StockInfo.save($scope.stockInfo, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
