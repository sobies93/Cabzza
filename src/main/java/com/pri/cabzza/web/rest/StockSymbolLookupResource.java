package com.pri.cabzza.web.rest;

import com.pri.cabzza.dataproviders.yahoo.YahooStockNameLookupProvider;
import com.pri.cabzza.dataproviders.yahoo.model.StockNameLookupResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rasgrass
 */
@RestController
@Slf4j
public class StockSymbolLookupResource {

	private final YahooStockNameLookupProvider yahooStockNameLookupProvider = new YahooStockNameLookupProvider();

	@RequestMapping("/lookup")
	public ResponseEntity<StockNameLookupResultSet> getStockNameLookup(@RequestParam("query") String query) {
		return new ResponseEntity<>(yahooStockNameLookupProvider.getLookupResult(query), HttpStatus.OK);
	}
}
