package com.pri.cabzza.service.util;

import com.pri.cabzza.domain.StockQuotes;

import java.util.Comparator;

/**
 * Created by ja on 2016-01-08.
 */
import java.util.Comparator;

public class StockQuotesComperator  implements Comparator<StockQuotes> {

    @Override
    public int compare(StockQuotes quotes1, StockQuotes quotes2) {
        return quotes1.getDate().compareTo(quotes2.getDate());
    }

}
