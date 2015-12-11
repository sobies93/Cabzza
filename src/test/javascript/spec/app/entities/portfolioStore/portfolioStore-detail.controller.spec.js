'use strict';

describe('PortfolioStore Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPortfolioStore, MockNewStockWallet, MockStockInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPortfolioStore = jasmine.createSpy('MockPortfolioStore');
        MockNewStockWallet = jasmine.createSpy('MockNewStockWallet');
        MockStockInfo = jasmine.createSpy('MockStockInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PortfolioStore': MockPortfolioStore,
            'NewStockWallet': MockNewStockWallet,
            'StockInfo': MockStockInfo
        };
        createController = function() {
            $injector.get('$controller')("PortfolioStoreDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'cabzzaApp:portfolioStoreUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
