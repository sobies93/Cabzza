package com.pri.cabzza.dataproviders.yahoo;

import java.util.Date;

/**
 *
 * @author Mikolaj Manski
 */
public abstract class AbstractYahooUrlBuilder {

	protected Date startDate;

	protected Date endDate;

	protected String quoteSymbol;

	public abstract String build();

	public AbstractYahooUrlBuilder withStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public AbstractYahooUrlBuilder withEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public AbstractYahooUrlBuilder withQuoteSymbol(String quoteSymbol) {
		this.quoteSymbol = quoteSymbol;
		return this;
	}
}
