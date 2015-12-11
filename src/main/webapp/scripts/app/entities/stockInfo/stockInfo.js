'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockInfo', {
                parent: 'entity',
                url: '/stockInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockInfo/stockInfos.html',
                        controller: 'StockInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stockInfo.detail', {
                parent: 'entity',
                url: '/stockInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockInfo/stockInfo-detail.html',
                        controller: 'StockInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StockInfo', function($stateParams, StockInfo) {
                        return StockInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stockInfo.new', {
                parent: 'stockInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockInfo/stockInfo-dialog.html',
                        controller: 'StockInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    symbol: null,
                                    quotesStartDate: null,
                                    quotesEndDate: null,
                                    isInvestorModeAvaiable: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stockInfo', null, { reload: true });
                    }, function() {
                        $state.go('stockInfo');
                    })
                }]
            })
            .state('stockInfo.edit', {
                parent: 'stockInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockInfo/stockInfo-dialog.html',
                        controller: 'StockInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockInfo', function(StockInfo) {
                                return StockInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
