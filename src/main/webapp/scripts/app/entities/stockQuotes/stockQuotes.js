'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockQuotes', {
                parent: 'entity',
                url: '/stockQuotess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockQuotes.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockQuotes/stockQuotess.html',
                        controller: 'StockQuotesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockQuotes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stockQuotes.detail', {
                parent: 'entity',
                url: '/stockQuotes/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockQuotes.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockQuotes/stockQuotes-detail.html',
                        controller: 'StockQuotesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockQuotes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StockQuotes', function($stateParams, StockQuotes) {
                        return StockQuotes.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stockQuotes.new', {
                parent: 'stockQuotes',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockQuotes/stockQuotes-dialog.html',
                        controller: 'StockQuotesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    value: null,
                                    splitRate: null,
                                    dividend: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stockQuotes', null, { reload: true });
                    }, function() {
                        $state.go('stockQuotes');
                    })
                }]
            })
            .state('stockQuotes.edit', {
                parent: 'stockQuotes',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockQuotes/stockQuotes-dialog.html',
                        controller: 'StockQuotesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockQuotes', function(StockQuotes) {
                                return StockQuotes.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockQuotes', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
