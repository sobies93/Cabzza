'use strict';

angular.module('cabzzaApp')
		.controller('StockFormController',function ($scope, $state, $rootScope) {
			$scope.datepickerOptions = {
				format: 'dd/MM/yyyy',
				language: 'pl',
				autoclose: true,
				weekStart: 0
			},
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
					$state.go('step6');
				} else if ($state.current.name === 'step6') {
					$state.go('home');
				}
			};


		});
