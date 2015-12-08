'use strict';

angular.module('cabzzaApp')
    .factory('Calculate', function ($resource, DateUtils) {
        return $resource('api/calculate', {}, {
            'start': {method: 'PUT'}
        });
    });
