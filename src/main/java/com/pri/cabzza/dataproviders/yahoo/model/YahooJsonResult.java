package com.pri.cabzza.dataproviders.yahoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mikolaj Manski
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooJsonResult {

	private QueryElement query;

}
