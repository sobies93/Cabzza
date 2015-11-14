package com.pri.cabzza.dataproviders.yahoo;

import com.pri.cabzza.dataproviders.yahoo.model.YahooJsonResult;
import com.pri.cabzza.dataproviders.yahoo.model.HistoricalDataQuote;
import com.pri.cabzza.dataproviders.DataProvider;
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.yahoo.model.QueryElement;
import com.pri.cabzza.dataproviders.yahoo.model.ResultsElement;
import com.pri.cabzza.model.quote.StockQuote;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mikolaj Manski
 */
@Singleton
@Service
public class YahooDataProvider implements DataProvider<List<? extends StockQuote>> {

	private final RestTemplate restTemplate;

	public YahooDataProvider() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	public List<HistoricalDataQuote> getStockQuotes(String symbol, String market, Date startDate, Date endDate) throws DataProviderPersistanceException {

		final String url = new HistoricalDataUrlBuilder()
				.withStartDate(startDate)
				.withEndDate(endDate)
				.withQuoteSymbol(symbol)
				.build();

		final ResponseEntity<YahooJsonResult> json = restTemplate.getForEntity(url, YahooJsonResult.class);
		final YahooJsonResult result = json.getBody();
		final List<HistoricalDataQuote> quotes = validateAndGetResult(result);

		return quotes;
	}

	private List<HistoricalDataQuote> validateAndGetResult(final YahooJsonResult result) throws DataProviderPersistanceException {
		List<HistoricalDataQuote> quotes = Collections.EMPTY_LIST;
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
			throw new DataProviderPersistanceException("Failed to get valid response");
		}
		return quotes;
	}

	@Override
	public void storeStockQuote(StockQuote quote) throws DataProviderPersistanceException {
	}

}