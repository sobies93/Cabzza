'use strict';

angular.module('cabzzaApp')
		.config(function ($stateProvider) {
			$stateProvider
					.state('stockForm', {
						abstract: true,
						parent: 'site',
						url: '/stockWalletForm',
						data: {
							authorities: ['ROLE_USER']
						}
					})
					.state('step1', {
						parent: 'stockForm',
						url: '/1',
						views: {
							'content@': {
								templateUrl: 'scripts/app/stockForm/step1.html',
								controller: 'StockFormController'
							}
						},
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					})
					.state('step2', {
						parent: 'stockForm',
						url: '/2',
						views: {
							'content@': {
								templateUrl: 'scripts/app/stockForm/step2.html',
								controller: 'StockFormController'
							}
						},
						resolve: {
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
									$translatePartialLoader.addPart('main');
									return $translate.refresh();
								}]
						}
					}).state('step3', {
				parent: 'stockForm',
				url: '/3',
				views: {
					'content@': {
						templateUrl: 'scripts/app/stockForm/step3.html',
						controller: 'StockFormController'
					}
				},
				resolve: {
					mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('main');
							return $translate.refresh();
						}]
				}
			}).state('step4', {
				parent: 'stockForm',
				url: '/4',
				views: {
					'content@': {
						templateUrl: 'scripts/app/stockForm/step4.html',
						controller: 'StockFormController'
					}
				},
				resolve: {
					mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('main');
							return $translate.refresh();
						}]
				}
			}).state('step5', {
				parent: 'stockForm',
				url: '/5',
				views: {
					'content@': {
						templateUrl: 'scripts/app/stockForm/step5.html',
						controller: 'StockFormController'
					}
				},
				resolve: {
					mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('main');
							return $translate.refresh();
						}]
				}
			}).state('step6', {
				parent: 'stockForm',
				url: '/6',
				views: {
					'content@': {
						templateUrl: 'scripts/app/stockForm/step6.html',
						controller: 'StockFormController'
					}
				},
				resolve: {
					mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
							$translatePartialLoader.addPart('main');
							return $translate.refresh();
						}]
				}
			});
		});

