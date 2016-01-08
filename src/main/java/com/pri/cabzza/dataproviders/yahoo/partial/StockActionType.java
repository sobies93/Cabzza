package com.pri.cabzza.dataproviders.yahoo.partial;

/**
 *
 * @author Mikolaj Manski
 */
public enum StockActionType {

	DIVIDEND("DIVIDEND"),
	SPLIT("SPLIT");

	private final String key;

	StockActionType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
