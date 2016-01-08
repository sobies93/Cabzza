package com.pri.cabzza.dataproviders.yahoo;

import com.pri.cabzza.dataproviders.yahoo.model.YahooCsvResult;
import com.pri.cabzza.dataproviders.yahoo.partial.DividendsAndSplitsDataProvider;
import com.pri.cabzza.dataproviders.yahoo.partial.StockActionType;
import com.pri.cabzza.dataproviders.yahoo.model.HistoricalDataQuote;
import com.pri.cabzza.dataproviders.PersistantDataProvider;
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.yahoo.partial.StockQuotesDataProvider;
import com.pri.cabzza.domain.StockInfo;
import com.pri.cabzza.domain.StockQuotes;
import com.pri.cabzza.repository.StockQuotesRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mikolaj Manski
 */
@Singleton
@Service
@Slf4j
public class YahooDataProvider implements PersistantDataProvider<List<StockQuotes>> {

	@Autowired
	private StockQuotesRepository stockQuotesRepository;

	@Autowired
	private StockQuotesDataProvider stockQuotesDataProvider;

	@Autowired
	private DividendsAndSplitsDataProvider dividendsAndSplitsDataProvider;

	@Override
	public List<StockQuotes> getData(String symbol, String market, Date startDate, Date endDate) throws DataProviderPersistanceException {
		final List<YahooCsvResult> dividendsAndSplits = dividendsAndSplitsDataProvider.getData(symbol, market, startDate, endDate);
		final List<HistoricalDataQuote> stockQuotes = stockQuotesDataProvider.getData(symbol, market, startDate, endDate);

		return convertToStockQuotes(mergeCsvAndJsonResults(stockQuotes, dividendsAndSplits), startDate, endDate);

	}

	@Override
	public void storeData(List<StockQuotes> quotes) throws DataProviderPersistanceException {
		stockQuotesRepository.save(quotes);
	}

	private List<HistoricalDataQuote> mergeCsvAndJsonResults(final List<HistoricalDataQuote> quotes, final List<YahooCsvResult> dividendsAndSplits) throws NumberFormatException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (HistoricalDataQuote historicalDataQuote : quotes) {
			for (YahooCsvResult yahooCsvResult : dividendsAndSplits) {
				if (sdf.format(historicalDataQuote.getDate()).equals(sdf.format(yahooCsvResult.getDate()))) {
					if (yahooCsvResult.getType().equals(StockActionType.DIVIDEND)) {
						historicalDataQuote.setDividend(Double.valueOf(yahooCsvResult.getValue()));
					} else {
						historicalDataQuote.setStockSplit(yahooCsvResult.getValue());
					}
				}
			}
		}
		return quotes;
	}

	private List<StockQuotes> convertToStockQuotes(List<HistoricalDataQuote> historicalDataQuotes, Date startDate, Date endDate) {
		final List<StockQuotes> result = Lists.newArrayList();
		for (HistoricalDataQuote historicalDataQuote : historicalDataQuotes) {
			final StockQuotes stockQuotes = new StockQuotes();
			stockQuotes.setDate(dateToLocalDate(historicalDataQuote.getDate()));
			stockQuotes.setDividend(historicalDataQuote.getDividend());
			stockQuotes.setSplitRate(parseStockSplit(historicalDataQuote.getStockSplit()));
			stockQuotes.setValue(historicalDataQuote.getClosePrice());

			final StockInfo stockInfo = new StockInfo();
			stockInfo.setIsInvestorModeAvaiable(Boolean.FALSE);
			stockInfo.setQuotesEndDate(dateToLocalDate(endDate));
			stockInfo.setQuotesStartDate(dateToLocalDate(startDate));
			stockInfo.setName(historicalDataQuote.getName());
			stockInfo.setSymbol(historicalDataQuote.getSymbol());

			stockQuotes.setStockInfo(stockInfo);
			result.add(stockQuotes);
		}
		return result;
	}

	private LocalDate dateToLocalDate(Date input) {
		return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private double parseStockSplit(String stockSplit) {
		if (StringUtils.isNoneBlank(stockSplit)) {
			int index = stockSplit.indexOf(":");
			if (index != -1) {
				double dividend = Integer.getInteger(Character.toString(stockSplit.charAt(index - 1)));
				double divider = Integer.getInteger(Character.toString(stockSplit.charAt(index + 1)));
				return dividend / divider;
			}
		}
		return 1;
	}
}
