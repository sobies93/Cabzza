'use strict';

angular.module('cabzzaApp').controller('NewStockWalletDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'NewStockWallet', 'User', 'PortfolioStore',
        function($scope, $stateParams, $modalInstance, entity, NewStockWallet, User, PortfolioStore) {

        $scope.newStockWallet = entity;
        $scope.users = User.query();
        $scope.portfoliostores = PortfolioStore.query();
        $scope.load = function(id) {
            NewStockWallet.get({id : id}, function(result) {
                $scope.newStockWallet = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('cabzzaApp:newStockWalletUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.newStockWallet.id != null) {
                NewStockWallet.update($scope.newStockWallet, onSaveFinished);
            } else {
                NewStockWallet.save($scope.newStockWallet, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
