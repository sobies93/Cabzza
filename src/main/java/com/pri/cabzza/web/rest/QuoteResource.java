package com.pri.cabzza.web.rest;

import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.yahoo.YahooDataProvider;
import com.pri.cabzza.domain.StockQuotes;
import com.pri.cabzza.web.rest.errors.WrongDateRangeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rasgrass
 */
@RestController
@Slf4j
public class QuoteResource {

	@Inject
	private YahooDataProvider dataProvider;

	@RequestMapping(value = "/quotes", method = RequestMethod.POST)
	public ResponseEntity<List<? extends StockQuotes>> getQuotes(@RequestParam("startDate") String startDateParam, @RequestParam("endDate") String endDateParam, @RequestParam("quoteSymbol") String quoteSymbol) {
		List<StockQuotes> stockQuotes = Collections.EMPTY_LIST;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			final Date startDate = sdf.parse(startDateParam);
			final Date endDate = sdf.parse(endDateParam);
			if (endDate.getYear() - startDate.getYear() > 1) {
				throw new WrongDateRangeException();
			}
			stockQuotes = dataProvider.getData(quoteSymbol, StringUtils.EMPTY, startDate, endDate);
			dataProvider.storeData(stockQuotes);
		} catch (ParseException | DataProviderPersistanceException ex) {
			log.error(ex.getMessage(), ex);
		}
		return new ResponseEntity<>(stockQuotes, HttpStatus.CREATED);
	}

}
