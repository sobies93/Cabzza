'use strict';

angular.module('cabzzaApp')
    .controller('QuotesListController', function ($scope, quoteService) {
        $scope.quotes = quoteService.get();
    });
