'use strict';

angular.module('cabzzaApp')
    .controller('StockFormController', function ($scope, $state, $rootScope, Calculate, StockInfoByMode) {


        //Calculate.start();
        $scope.init = function () {
            $scope.$parent.stockWallet = {};
            $scope.$parent.transferObject = {};
        }
        if ($state.current.name === 'step1') {
            $scope.init();
        }

        $scope.atLeastOne = function () {
            if($scope.$parent.transferObject.isChosen == null) {
                return false
            }
            for(var i = 0; i < $scope.$parent.transferObject.stocks.length; i++){
                if($scope.$parent.transferObject.isChosen[i]) {
                    return true;
                }
            }
            return false;
        }

        $scope.next = function () {
            if ($state.current.name === 'step1') {
                StockInfoByMode.get({isInvestor: ($scope.stockWallet.isInvestor ? 'investor' : 'student')}, function (result)  {
                    $scope.$parent.transferObject.stocks = result;
                    $state.go('step2');
                });
            } else if ($state.current.name === 'step2') {
                $state.go('step3');
            } else if ($state.current.name === 'step3') {
                 $rootScope.$broadcast('sliders');
                $state.go('step4');
            } else if ($state.current.name === 'step4') {
                $state.go('step5');
            } else if ($state.current.name === 'step5') {
                $state.go('projectView');
            }
        };

    });
