'use strict';

angular.module('cabzzaApp')
		.config(function ($stateProvider) {
			$stateProvider
					.state('stockForm', {
						parent: 'site',
						url: '/stockWalletForm',
						data: {
							authorities: ['ROLE_USER']
						},
						views: {
							'content@': {
								templateUrl: 'scripts/app/stockForm/form.html',
								controller: 'StockFormController'
							}
						}
					})
					.state('stockForm.step1', {
						parent: 'stockForm',
						url: '/1',
						templateUrl: 'scripts/app/stockForm/step1.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('stockForm.step2', {
						parent: 'stockForm',
						url: '/2',
						templateUrl: 'scripts/app/stockForm/step2.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('stockForm.step3', {
						parent: 'stockForm',
						url: '/3',
						templateUrl: 'scripts/app/stockForm/step3.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('stockForm.step4', {
						parent: 'stockForm',
						url: '/4',
						templateUrl: 'scripts/app/stockForm/step4.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('stockForm.step5', {
						parent: 'stockForm',
						url: '/5',
						templateUrl: 'scripts/app/stockForm/step5.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('stockForm.step6', {
						parent: 'stockForm',
						url: '/6',
						templateUrl: 'scripts/app/stockForm/step6.html',
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					});
		});

