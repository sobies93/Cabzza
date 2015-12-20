'use strict';

angular.module('cabzzaApp')
		.controller('StockFormController', function ($scope, $state, StockWallet, Account, filterFilter, $rootScope) {
			$scope.datepickerOptions = {
				language: 'pl',
				autoclose: true,
				weekStart: 0,
				format: 'dd/mm/yyyy'
			},
			$scope.formData = {};

			$scope.quotes = [
				{symbol: 'MSFT', name: "Microsoft Corporation", selected: false},
				{symbol: 'YHOO', name: "Yahoo", selected: false},
				{symbol: 'GOOG', name: "Google", selected: false},
				{symbol: 'AAPL', name: "Apple", selected: false}
			];

			$scope.selectedQuotes = function selectedQuotes() {
				return filterFilter($scope.quotes, {selected: true});
			};

			$scope.selection = [];

			$scope.$watch('quotes|filter:{selected:true}', function (nv) {
				$scope.selection = nv.map(function (quote) {
					return quote.symbol;
				});
			}, true);


			$scope.formSubmit = function () {
				Account.get().$promise
						.then(function (account) {
							$scope.formData.ownerId = account.data.id;
							$scope.formData.startDate = new Date($scope.formData.startDate);
							$scope.formData.endDate = new Date($scope.formData.endDate);
							$scope.formData.quoteSymbols = $scope.selection.toString();
							StockWallet.save($scope.formData);
							$state.go('stockForm.step6');
						});
			};

            $scope.next = function () {
                    if ($state.current.name === 'stockForm.step1') {
                        $state.go('stockForm.step2');
                    } else if ($state.current.name === 'stockForm.step2') {
                        $state.go('stockForm.step3');
                    } else if ($state.current.name === 'stockForm.step3') {
                         $rootScope.$broadcast('stockForm.sliders');
                        $state.go('stockForm.step4');
                    } else if ($state.current.name === 'stockForm.step4') {
                        $state.go('stockForm.step5');
                    } else if ($state.current.name === 'stockForm.step5') {
                        $state.go('projectView');
                    }
                };


		});
