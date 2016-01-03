'use strict';

angular.module('cabzzaApp')
    .factory('StockInfo', function ($resource, DateUtils) {
        return $resource('api/stockInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.quotesStartDate = DateUtils.convertLocaleDateFromServer(data.quotesStartDate);
                    data.quotesEndDate = DateUtils.convertLocaleDateFromServer(data.quotesEndDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.quotesStartDate = DateUtils.convertLocaleDateToServer(data.quotesStartDate);
                    data.quotesEndDate = DateUtils.convertLocaleDateToServer(data.quotesEndDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.quotesStartDate = DateUtils.convertLocaleDateToServer(data.quotesStartDate);
                    data.quotesEndDate = DateUtils.convertLocaleDateToServer(data.quotesEndDate);
                    return angular.toJson(data);
                }
            }
        });
    });

angular.module('cabzzaApp')
    .factory('StockInfoByMode', function ($resource, DateUtils) {
        return $resource('api/stockInfosByMode/:isInvestor', {}, {
            'get': {
                isArray:true,
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.quotesStartDate = DateUtils.convertLocaleDateFromServer(data.quotesStartDate);
                    data.quotesEndDate = DateUtils.convertLocaleDateFromServer(data.quotesEndDate);
                    return data;
                }
            }
        });
    });
