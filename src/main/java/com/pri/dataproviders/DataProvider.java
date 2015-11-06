package com.pri.dataproviders;

import com.pri.dataproviders.exceptions.DataProviderException;
import com.pri.dataproviders.exceptions.DataProviderPersistanceException;
import com.pri.model.quote.StockQuote;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Mikolaj Manski
 * @param <T>
 */
public interface DataProvider<T extends List<? extends StockQuote>> {

	T getStockQuotes(String symbol, String market, Date startDate, Date endDate) throws DataProviderException;

	void storeStockQuote(StockQuote quote) throws DataProviderPersistanceException;

}
