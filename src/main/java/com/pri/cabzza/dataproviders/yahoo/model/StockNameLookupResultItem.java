package com.pri.cabzza.dataproviders.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rasgrass
 */

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class StockNameLookupResultItem implements Serializable{
	private String symbol;
	private String name;
	private String exch;
	private String type;
	private String exchDisp;
	private String typeDisp;
}
