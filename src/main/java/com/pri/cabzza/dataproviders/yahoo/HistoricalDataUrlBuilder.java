package com.pri.cabzza.dataproviders.yahoo;

import java.text.SimpleDateFormat;

/**
 *
 * @author Mikolaj Manski
 */
public class HistoricalDataUrlBuilder extends AbstractYahooUrlBuilder {

	private static final String URL_PATTERN
			= "https://query.yahooapis.com/v1/public/yql?q="
			+ "select * from yahoo.finance.historicaldata where symbol = \"%s\" "
			+ "and startDate = \"%s\" and endDate = \"%s\""
			+ "&format=json&diagnostics=true"
			+ "&env=http://datatables.org/alltables.env&callback=";

	@Override
	public String build() {
		final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		return String.format(URL_PATTERN, quoteSymbol, sdf.format(startDate), sdf.format(endDate));
	}

}
