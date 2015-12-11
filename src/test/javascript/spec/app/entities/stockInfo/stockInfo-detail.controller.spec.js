'use strict';

describe('StockInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStockInfo, MockStockQuotes, MockPortfolioStore;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStockInfo = jasmine.createSpy('MockStockInfo');
        MockStockQuotes = jasmine.createSpy('MockStockQuotes');
        MockPortfolioStore = jasmine.createSpy('MockPortfolioStore');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StockInfo': MockStockInfo,
            'StockQuotes': MockStockQuotes,
            'PortfolioStore': MockPortfolioStore
        };
        createController = function() {
            $injector.get('$controller')("StockInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'cabzzaApp:stockInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
