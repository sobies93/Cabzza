'use strict';

angular.module('cabzzaApp')
    .factory('NewStockWalletSearch', function ($resource) {
        return $resource('api/_search/newStockWallets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
