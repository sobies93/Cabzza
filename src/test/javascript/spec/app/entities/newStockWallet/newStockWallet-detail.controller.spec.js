'use strict';

describe('NewStockWallet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNewStockWallet, MockUser, MockPortfolioStore;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNewStockWallet = jasmine.createSpy('MockNewStockWallet');
        MockUser = jasmine.createSpy('MockUser');
        MockPortfolioStore = jasmine.createSpy('MockPortfolioStore');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NewStockWallet': MockNewStockWallet,
            'User': MockUser,
            'PortfolioStore': MockPortfolioStore
        };
        createController = function() {
            $injector.get('$controller')("NewStockWalletDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'cabzzaApp:newStockWalletUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
