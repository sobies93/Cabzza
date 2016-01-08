package com.pri.cabzza.dataproviders.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.LinkedList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rasgrass
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockNameLookupResult implements Serializable {

	@JsonProperty("Query")
	private String query;

	@JsonProperty("Result")
	private LinkedList<StockNameLookupResultItem> result;

}
