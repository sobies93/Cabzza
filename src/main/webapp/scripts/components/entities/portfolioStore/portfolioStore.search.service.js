'use strict';

angular.module('cabzzaApp')
    .factory('PortfolioStoreSearch', function ($resource) {
        return $resource('api/_search/portfolioStores/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
