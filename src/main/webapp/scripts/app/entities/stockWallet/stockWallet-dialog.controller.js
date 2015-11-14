'use strict';

angular.module('cabzzaApp').controller('StockWalletDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StockWallet', 'User',
        function($scope, $stateParams, $modalInstance, entity, StockWallet, User) {

        $scope.stockWallet = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            StockWallet.get({id : id}, function(result) {
                $scope.stockWallet = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('cabzzaApp:stockWalletUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stockWallet.id != null) {
                StockWallet.update($scope.stockWallet, onSaveFinished);
            } else {
                StockWallet.save($scope.stockWallet, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
