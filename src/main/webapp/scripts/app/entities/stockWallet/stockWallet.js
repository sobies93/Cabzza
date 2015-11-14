'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockWallet', {
                parent: 'entity',
                url: '/stockWallets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockWallet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockWallet/stockWallets.html',
                        controller: 'StockWalletController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockWallet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stockWallet.detail', {
                parent: 'entity',
                url: '/stockWallet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.stockWallet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockWallet/stockWallet-detail.html',
                        controller: 'StockWalletDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stockWallet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StockWallet', function($stateParams, StockWallet) {
                        return StockWallet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stockWallet.new', {
                parent: 'stockWallet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockWallet/stockWallet-dialog.html',
                        controller: 'StockWalletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quoteSymbol: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stockWallet', null, { reload: true });
                    }, function() {
                        $state.go('stockWallet');
                    })
                }]
            })
            .state('stockWallet.edit', {
                parent: 'stockWallet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stockWallet/stockWallet-dialog.html',
                        controller: 'StockWalletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockWallet', function(StockWallet) {
                                return StockWallet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockWallet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
