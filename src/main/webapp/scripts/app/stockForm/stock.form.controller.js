'use strict';

angular.module('cabzzaApp')
		.controller('StockFormController', function ($scope, $state, walletFormService, StockWallet, DateUtils, Account) {
			$scope.datepickerOptions = {
				language: 'pl',
				autoclose: true,
				weekStart: 0
			},
			$scope.next = function () {
				if ($state.current.name === 'step1') {
					$state.go('step2');
				} else if ($state.current.name === 'step2') {
					walletFormService.wallet.walletName = $scope.name;
					$state.go('step3');
				} else if ($state.current.name === 'step3') {
					walletFormService.wallet.quoteSymbols = 'MSFT';
					$state.go('step4');
				} else if ($state.current.name === 'step4') {
					walletFormService.wallet.profit = $scope.profit;
					walletFormService.wallet.security = $scope.security;
					walletFormService.wallet.optimalWallet = $scope.optimalWallet;
					$state.go('step5');
				} else if ($state.current.name === 'step5') {
					walletFormService.wallet.startDate = DateUtils.convertLocaleDateToServer(new Date($scope.startDate));
					walletFormService.wallet.timeMiddle = $scope.middleDate;
					walletFormService.wallet.endDate = DateUtils.convertLocaleDateToServer(new Date($scope.endDate));
					var id;
					Account.get().$promise
							.then(function (account) {
								id = account.data.id;
								walletFormService.wallet.ownerId = id;
								StockWallet.save(walletFormService.wallet);
								$state.go('step6');
							});
				} else if ($state.current.name === 'step6') {
					$state.go('home');
				}
			};


		});
