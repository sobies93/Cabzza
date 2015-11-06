package com.pri.dataproviders.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mikolaj Manski
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsElement {

	@JsonProperty("quote")
	private List<YahooHistoricalDataQuote> quotes;

}
