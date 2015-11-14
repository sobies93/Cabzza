'use strict';

angular.module('cabzzaApp')
		.config(function ($stateProvider) {
			$stateProvider
					.state('step1', {
						parent: 'site',
						url: '/stockWallet',
						data: {
							authorities: []
						},
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
						parent: 'site',
						url: '/stockWallet',
						data: {
							authorities: []
						},
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
				parent: 'site',
				url: '/stockWallet',
				data: {
					authorities: []
				},
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
				parent: 'site',
				url: '/stockWallet',
				data: {
					authorities: []
				},
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
				parent: 'site',
				url: '/stockWallet',
				data: {
					authorities: []
				},
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
				parent: 'site',
				url: '/stockWallet',
				data: {
					authorities: []
				},
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

