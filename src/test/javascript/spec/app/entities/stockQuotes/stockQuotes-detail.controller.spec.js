'use strict';

describe('StockQuotes Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStockQuotes, MockStockInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStockQuotes = jasmine.createSpy('MockStockQuotes');
        MockStockInfo = jasmine.createSpy('MockStockInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StockQuotes': MockStockQuotes,
            'StockInfo': MockStockInfo
        };
        createController = function() {
            $injector.get('$controller')("StockQuotesDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'cabzzaApp:stockQuotesUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
