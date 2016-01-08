package com.pri.cabzza.dataproviders.yahoo.model;

import com.pri.cabzza.dataproviders.yahoo.partial.StockActionType;

import java.util.Date;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Mikolaj Manski
 */
@Data
@RequiredArgsConstructor
public class YahooCsvResult {

	private final StockActionType type;

	private final Date date;

	private final String value;

}
