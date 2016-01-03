'use strict';

angular.module('cabzzaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectView', {
                parent: 'site',
                url: '/projectView/{id}',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/projectView/projectView.html',
                        controller: 'ProjectViewController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('main');
                            return $translate.refresh();
                        }]
                }
            })
        });
