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
