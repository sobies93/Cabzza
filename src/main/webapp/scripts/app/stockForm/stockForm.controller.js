'use strict';

angular.module('cabzzaApp')
		.controller('StockFormController', function ($scope, $state, $rootScope, Calculate) {


            Calculate.start();

			$scope.next = function () {
				if ($state.current.name === 'step1') {
					$state.go('step2');
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
