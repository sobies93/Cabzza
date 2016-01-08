package com.pri.cabzza.dataproviders.yahoo.partial;

import java.text.SimpleDateFormat;

/**
 *
 * @author Mikolaj Manski
 */
class HistoricalDataUrlBuilder extends AbstractYahooUrlBuilder {

	private final SimpleDateFormat sdf;

	public HistoricalDataUrlBuilder() {
		sdf = new SimpleDateFormat("YYYY-MM-dd");

	}

	private static final String URL_PATTERN
			= "https://query.yahooapis.com/v1/public/yql?q="
			+ "select * from yahoo.finance.historicaldata where symbol = \"%s\" "
			+ "and startDate = \"%s\" and endDate = \"%s\""
			+ "&format=json&diagnostics=true"
			+ "&env=http://datatables.org/alltables.env&callback=";

	@Override
	public String build() {
		return String.format(URL_PATTERN, quoteSymbol, sdf.format(startDate), sdf.format(endDate));
	}
}
