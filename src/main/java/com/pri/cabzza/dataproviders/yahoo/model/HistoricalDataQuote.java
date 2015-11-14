package com.pri.cabzza.dataproviders.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pri.cabzza.model.quote.StockQuote;

import java.util.Date;

import lombok.Data;


/**
 *
 * @author Mikolaj Manski
 */
@Data
public class HistoricalDataQuote implements StockQuote {

	@JsonIgnore
	private String name;

	@JsonProperty("Symbol")
	private String symbol;

	@JsonProperty("Date")
	private Date date;

	@JsonIgnore
	private String stockSplit;

	@JsonIgnore
	private double dividend;

	@JsonProperty("Open")
	private double openPrice;

	@JsonProperty("High")
	private double highPrice;

	@JsonProperty("Low")
	private double lowPrice;

	@JsonProperty("Close")
	private double closePrice;

	@JsonProperty("Volume")
	private double volume;

	@JsonProperty("Adj_Close")
	private double adjustedVolume;

}