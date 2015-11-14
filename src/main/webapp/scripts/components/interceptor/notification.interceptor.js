 'use strict';

angular.module('cabzzaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-cabzzaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-cabzzaApp-params')});
                }
                return response;
            }
        };
    });
