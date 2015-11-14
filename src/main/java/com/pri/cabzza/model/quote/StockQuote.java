package com.pri.cabzza.model.quote;

import java.util.Date;

/**
 *
 * @author Mikolaj Manski
 */
public interface StockQuote {

	String getName();

	String getSymbol();

	Date getDate();

	String getStockSplit();

	double getDividend();

	double getOpenPrice();

	double getHighPrice();

	double getLowPrice();

	double getClosePrice();

	double getVolume();

	double getAdjustedVolume();

}
