package com.pri.dataproviders.yahoo;

import com.pri.dataproviders.yahoo.model.YahooJsonResult;
import com.pri.dataproviders.yahoo.model.YahooHistoricalDataQuote;
import com.pri.dataproviders.DataProvider;
import com.pri.dataproviders.exceptions.DataProviderException;
import com.pri.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.dataproviders.yahoo.model.QueryElement;
import com.pri.dataproviders.yahoo.model.ResultsElement;
import com.pri.model.quote.StockQuote;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mikolaj Manski
 */
@Singleton
public class YahooDataProvider implements DataProvider<List<? extends StockQuote>> {

	private final RestTemplate restTemplate;

	public YahooDataProvider() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	public List<YahooHistoricalDataQuote> getStockQuotes(String symbol, String market, Date startDate, Date endDate) throws DataProviderException {

		final String url = new HistoricalDataUrlBuilder()
				.withStartDate(startDate)
				.withEndDate(endDate)
				.withQuoteSymbol(symbol)
				.build();

		final ResponseEntity<YahooJsonResult> json = restTemplate.getForEntity(url, YahooJsonResult.class);
		final YahooJsonResult result = json.getBody();
		final List<YahooHistoricalDataQuote> quotes = validateAndGetResult(result);

		return quotes;
	}

	private List<YahooHistoricalDataQuote> validateAndGetResult(final YahooJsonResult result) throws DataProviderException {
		List<YahooHistoricalDataQuote> quotes = Collections.EMPTY_LIST;
		QueryElement queryElement = null;
		ResultsElement resultsElement = null;
		if (result != null) {
			queryElement = result.getQuery();
			if (queryElement != null) {
				resultsElement = queryElement.getResults();
				if (resultsElement != null) {
					quotes = resultsElement.getQuotes();
				}
			}
		}
		if (result == null || queryElement == null || resultsElement == null) {
			throw new DataProviderException("Failed to get valid response");
		}
		return quotes;
	}

	@Override
	public void storeStockQuote(StockQuote quote) throws DataProviderPersistanceException {
	}

}
