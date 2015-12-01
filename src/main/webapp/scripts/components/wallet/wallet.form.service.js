'use strict';

angular.module('cabzzaApp')
		.factory("walletFormService", function () {
			return {
				wallet: {
					quoteSymbols: 'MSFT',
					walletName: '',
					walletMode: 'MODE_INVEST',
					profit: 0.0,
					security: 0.0,
					optimalWallet: 0.0,
					startDate: null,
					endDate: null,
					timeMiddle: null
				}};
		});