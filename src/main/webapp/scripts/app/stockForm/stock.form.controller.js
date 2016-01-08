'use strict';

angular.module('cabzzaApp')
		.controller('StockFormController', function ($scope, $state, StockWallet, Account, filterFilter, $rootScope,
		StockInfoByMode,NewStockWallet, PortfolioStore, Calculation) {
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

            $scope.init = function () {
                $scope.$parent.stockWallet = {};
                $scope.$parent.transferObject = {};
            }
            if ($state.current.name === 'stockForm.step1') {
                $scope.init();
            }

            $scope.getMaxDate = function (a,b) {
                console.log(a);
                console.log(b);

                if(a.getTime() > b.getTime()) {
                    return a;
                }
                return b;
            };

            $scope.getMinDate = function (a,b) {
                if(a.getTime() > b.getTime()) {
                    return b;
                }
                return a;
            };

            $scope.calculateData = function (){
                $scope.$parent.maxDate = new Date();
                $scope.$parent.currentDate = new Date();
                $scope.$parent.minDate = new Date("1970-01-01");
                 for(var i = 0; i < $scope.$parent.transferObject.stocks.length; i++){
                        if($scope.$parent.transferObject.isChosen[i]) {
                            $scope.$parent.minDate = $scope.getMaxDate($scope.$parent.minDate, new Date ($scope.$parent.transferObject.stocks[i].quotesStartDate));
                            $scope.$parent.maxDate = $scope.getMinDate($scope.$parent.maxDate, new Date ($scope.$parent.transferObject.stocks[i].quotesEndDate));
                        }
                    }
                    return false;

            };

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

            $scope.dateValidation = function () {
                if(!$scope.stockWallet.prognoseDate) {
                    return false
                }
                if(!$scope.stockWallet.modeIsInvestor&&!$scope.stockWallet.calculatingsDate&&!$scope.stockWallet.historicalDataDate) {
                    return false;
                }
                return true;
            };

            $scope.portfolioPromiseHandler = function (stockId, walletId) {
                return new Promise(function(resolve, reject) {
                    PortfolioStore.save({ stockInfo : {id: stockId}, newStockWallet : {id: walletId}}, function (){
                        resolve(true);
                    });
                });
            }

            $scope.addStocks = function (walletId, callback) {
                var promises = [];
                for(var i = 0; i < $scope.$parent.transferObject.stocks.length; i++){
                    if($scope.$parent.transferObject.isChosen[i]) {
                         promises.push($scope.portfolioPromiseHandler($scope.$parent.transferObject.stocks[i].id,walletId));
                    }
                }
                Promise.all(promises).then( function() {
                    callback()
                });
            }

            $scope.next = function () {
                if ($state.current.name === 'stockForm.step1') {
                    StockInfoByMode.get({isInvestor: ($scope.stockWallet.modeIsInvestor ? 'investor' : 'student')}, function (result)  {
                        $scope.$parent.transferObject.stocks = result;
                        $state.go('stockForm.step2');
                    });
                } else if ($state.current.name === 'stockForm.step2') {
                    $state.go('stockForm.step3');
                } else if ($state.current.name === 'stockForm.step3') {
                    $rootScope.$broadcast('sliders');
                    $scope.calculateData();
                    $state.go('stockForm.step4');
                } else if ($state.current.name === 'stockForm.step4') {
                    if($scope.setReturn) {
                        $scope.$parent.stockWallet.expectedVariation = null;
                    } else {
                        $scope.$parent.stockWallet.expectedReturn = null;
                    }
                    $state.go('stockForm.step5');
                } else if ($state.current.name === 'stockForm.step5') {
                    $scope.$parent.stockWallet.id = null;
                    if(!$scope.$parent.stockWallet.historicalDataDate) {
                        var current = new Date ();
                        $scope.$parent.stockWallet.historicalDataDate = new Date (current.getTime() - ($scope.$parent.stockWallet.prognoseDate.getTime() - current.getTime()));
                    }
                    if(!$scope.$parent.stockWallet.calculatingsDate) {
                        $scope.$parent.stockWallet.calculatingsDate = new Date ();
                    }
                    NewStockWallet.save($scope.$parent.stockWallet, function (data){
                        $scope.addStocks(data.id, function(){
                            Calculation.make({walletId:data.id},function () {
                                $state.go('projectView');
                            });
                        });

                    });
                }
            };

		});
