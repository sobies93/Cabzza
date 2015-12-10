'use strict';

angular.module('cabzzaApp')
    .factory('StockInfoSearch', function ($resource) {
        return $resource('api/_search/stockInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
