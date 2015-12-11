'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('portfolioStore', {
                parent: 'entity',
                url: '/portfolioStores',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.portfolioStore.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/portfolioStore/portfolioStores.html',
                        controller: 'PortfolioStoreController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('portfolioStore');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('portfolioStore.detail', {
                parent: 'entity',
                url: '/portfolioStore/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cabzzaApp.portfolioStore.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/portfolioStore/portfolioStore-detail.html',
                        controller: 'PortfolioStoreDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('portfolioStore');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PortfolioStore', function($stateParams, PortfolioStore) {
                        return PortfolioStore.get({id : $stateParams.id});
                    }]
                }
            })
            .state('portfolioStore.new', {
                parent: 'portfolioStore',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/portfolioStore/portfolioStore-dialog.html',
                        controller: 'PortfolioStoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    percent: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('portfolioStore', null, { reload: true });
                    }, function() {
                        $state.go('portfolioStore');
                    })
                }]
            })
            .state('portfolioStore.edit', {
                parent: 'portfolioStore',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/portfolioStore/portfolioStore-dialog.html',
                        controller: 'PortfolioStoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PortfolioStore', function(PortfolioStore) {
                                return PortfolioStore.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('portfolioStore', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
