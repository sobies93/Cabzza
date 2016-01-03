'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newStockWallet', {
                parent: 'entity',
                url: '/newStockWallets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.newStockWallet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/newStockWallet/newStockWallets.html',
                        controller: 'NewStockWalletController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('newStockWallet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('newStockWallet.detail', {
                parent: 'entity',
                url: '/newStockWallet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.newStockWallet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/newStockWallet/newStockWallet-detail.html',
                        controller: 'NewStockWalletDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('newStockWallet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NewStockWallet', function($stateParams, NewStockWallet) {
                        return NewStockWallet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('newStockWallet.new', {
                parent: 'newStockWallet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/newStockWallet/newStockWallet-dialog.html',
                        controller: 'NewStockWalletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    historicalDataDate: null,
                                    calculatingsDate: null,
                                    prognoseDate: null,
                                    riskfreeRate: null,
                                    expectedReturn: null,
                                    expectedVariation: null,
                                    sharpRatio: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('newStockWallet', null, { reload: true });
                    }, function() {
                        $state.go('newStockWallet');
                    })
                }]
            })
            .state('newStockWallet.edit', {
                parent: 'newStockWallet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/newStockWallet/newStockWallet-dialog.html',
                        controller: 'NewStockWalletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NewStockWallet', function(NewStockWallet) {
                                return NewStockWallet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('newStockWallet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
