package com.pri.cabzza.dataproviders.yahoo.partial;

import com.pri.cabzza.dataproviders.DataProvider;
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.yahoo.model.HistoricalDataQuote;
import com.pri.cabzza.dataproviders.yahoo.model.QueryElement;
import com.pri.cabzza.dataproviders.yahoo.model.ResultsElement;
import com.pri.cabzza.dataproviders.yahoo.model.YahooJsonResult;

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
@Service
@Singleton
public class StockQuotesDataProvider implements DataProvider<List<HistoricalDataQuote>> {

	private final RestTemplate restTemplate;

	public StockQuotesDataProvider() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	public List<HistoricalDataQuote> getData(String symbol, String market, Date startDate, Date endDate) throws DataProviderPersistanceException {
		final String url = new HistoricalDataUrlBuilder()
				.withStartDate(startDate)
				.withEndDate(endDate)
				.withQuoteSymbol(symbol)
				.build();

		final ResponseEntity<YahooJsonResult> json = restTemplate.getForEntity(url, YahooJsonResult.class);
		final YahooJsonResult result = json.getBody();

		return validateAndGetResult(result);
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
}
