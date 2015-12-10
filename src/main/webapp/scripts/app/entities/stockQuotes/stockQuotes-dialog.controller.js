'use strict';

angular.module('cabzzaApp').controller('StockQuotesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StockQuotes', 'StockInfo',
        function($scope, $stateParams, $modalInstance, entity, StockQuotes, StockInfo) {

        $scope.stockQuotes = entity;
        $scope.stockinfos = StockInfo.query();
        $scope.load = function(id) {
            StockQuotes.get({id : id}, function(result) {
                $scope.stockQuotes = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('cabzzaApp:stockQuotesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stockQuotes.id != null) {
                StockQuotes.update($scope.stockQuotes, onSaveFinished);
            } else {
                StockQuotes.save($scope.stockQuotes, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
