'use strict';

angular.module('cabzzaApp')
    .factory('StockQuotesSearch', function ($resource) {
        return $resource('api/_search/stockQuotess/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
