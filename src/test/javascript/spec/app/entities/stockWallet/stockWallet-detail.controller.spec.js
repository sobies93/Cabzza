'use strict';

describe('StockWallet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStockWallet, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStockWallet = jasmine.createSpy('MockStockWallet');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StockWallet': MockStockWallet,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("StockWalletDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'cabzzaApp:stockWalletUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
