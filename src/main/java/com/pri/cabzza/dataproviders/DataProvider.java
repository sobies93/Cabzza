package com.pri.cabzza.dataproviders;

/**
 *
 * @author rasgrass
 */
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.cabzza.model.quote.StockQuote;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Mikolaj Manski
 * @param <T>
 */
public interface DataProvider<T extends List<? extends StockQuote>> {

	T getStockQuotes(String symbol, String market, Date startDate, Date endDate) throws DataProviderPersistanceException;

	void storeStockQuote(StockQuote quote) throws DataProviderPersistanceException;

}
