'use strict';

angular.module('cabzzaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


