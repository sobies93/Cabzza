'use strict';

angular.module('cabzzaApp')
    .factory('PortfolioStore', function ($resource, DateUtils) {
        return $resource('api/portfolioStores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

angular.module('cabzzaApp')
    .factory('PortfolioStoreByWallet', function ($resource, DateUtils) {
        return $resource('api/portfolioStoresByWallet/:id', {}, {
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
