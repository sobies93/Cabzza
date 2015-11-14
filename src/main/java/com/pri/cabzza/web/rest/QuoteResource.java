package com.pri.cabzza.web.rest;

import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.yahoo.YahooDataProvider;
import com.pri.cabzza.dataproviders.yahoo.model.HistoricalDataQuote;
import com.pri.cabzza.model.quote.StockQuote;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rasgrass
 */
@RestController
public class QuoteResource {
	
	@Inject
	private YahooDataProvider dataProvider;

	@RequestMapping(value = "/quotes", method = RequestMethod.POST)
	@ResponseBody
	public List<? extends StockQuote> getQuotes(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("quoteSymbol") String quoteSymbol) {
		List<HistoricalDataQuote> stockQuotes = Collections.EMPTY_LIST;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			stockQuotes = dataProvider.getStockQuotes(quoteSymbol, StringUtils.EMPTY, sdf.parse(startDate), sdf.parse(endDate));
		} catch (ParseException | DataProviderPersistanceException ex) {
			Logger.getLogger(QuoteResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return stockQuotes;
	}

}
