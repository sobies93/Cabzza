'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quote', {
                parent: 'site',
                url: '/quote',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER'],
                    pageTitle: 'quote.form.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/quote/form.html',
                        controller: 'QuoteFormController'
                    }
					
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quote');
                        return $translate.refresh();
                    }]
                }
            })
			.state('quotesList', {
                parent: 'site',
                url: '/list',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER'],
                    pageTitle: 'quote.form.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/quote/list.html',
                        controller: 'QuotesListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('quote');
                        return $translate.refresh();
                    }]
                }
            });
    });
