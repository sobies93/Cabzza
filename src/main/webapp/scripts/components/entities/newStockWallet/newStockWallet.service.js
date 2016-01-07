'use strict';

angular.module('cabzzaApp')
    .factory('NewStockWallet', function ($resource, DateUtils) {
        return $resource('api/newStockWallets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.historicalDataDate = DateUtils.convertLocaleDateFromServer(data.historicalDataDate);
                    data.calculatingsDate = DateUtils.convertLocaleDateFromServer(data.calculatingsDate);
                    data.prognoseDate = DateUtils.convertLocaleDateFromServer(data.prognoseDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.historicalDataDate = DateUtils.convertLocaleDateToServer(data.historicalDataDate);
                    data.calculatingsDate = DateUtils.convertLocaleDateToServer(data.calculatingsDate);
                    data.prognoseDate = DateUtils.convertLocaleDateToServer(data.prognoseDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.historicalDataDate = DateUtils.convertLocaleDateToServer(data.historicalDataDate);
                    data.calculatingsDate = DateUtils.convertLocaleDateToServer(data.calculatingsDate);
                    data.prognoseDate = DateUtils.convertLocaleDateToServer(data.prognoseDate);
                    return angular.toJson(data);
                }
            }
        });
    });

angular.module('cabzzaApp')
    .factory('UsersNewStockWallet', function ($resource, DateUtils) {
        return $resource('api/usersNewStockWallets/:id', {}, {
            'get': {
                isArray: true,
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.historicalDataDate = DateUtils.convertLocaleDateFromServer(data.historicalDataDate);
                    data.calculatingsDate = DateUtils.convertLocaleDateFromServer(data.calculatingsDate);
                    data.prognoseDate = DateUtils.convertLocaleDateFromServer(data.prognoseDate);
                    return data;
                }
            }
        });
    });

angular.module('cabzzaApp')
    .factory('Calculation', function ($resource, DateUtils) {
        return $resource('api/calculateStockWallet/:walletId', {}, {
            'make': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.historicalDataDate = DateUtils.convertLocaleDateFromServer(data.historicalDataDate);
                    data.calculatingsDate = DateUtils.convertLocaleDateFromServer(data.calculatingsDate);
                    data.prognoseDate = DateUtils.convertLocaleDateFromServer(data.prognoseDate);
                    return data;
                }
            }
        });
    });
