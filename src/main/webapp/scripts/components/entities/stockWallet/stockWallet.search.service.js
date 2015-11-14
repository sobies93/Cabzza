'use strict';

angular.module('cabzzaApp')
    .factory('StockWalletSearch', function ($resource) {
        return $resource('api/_search/stockWallets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
