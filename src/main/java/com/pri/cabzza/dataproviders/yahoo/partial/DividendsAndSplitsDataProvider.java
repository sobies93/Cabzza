package com.pri.cabzza.dataproviders.yahoo.partial;

import au.com.bytecode.opencsv.CSVReader;

import com.pri.cabzza.dataproviders.yahoo.model.YahooCsvResult;
import com.pri.cabzza.dataproviders.DataProvider;
import com.pri.cabzza.dataproviders.exceptions.DataProviderPersistanceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mikolaj Manski
 */
@Service
@Singleton
@Slf4j
public class DividendsAndSplitsDataProvider implements DataProvider<List<YahooCsvResult>> {

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<YahooCsvResult> getData(String symbol, String market, Date startDate, Date endDate) throws DataProviderPersistanceException {
		final String dividendAndSplitUrl = new DividendAndSplitUrlBuilder()
				.withStartDate(startDate)
				.withEndDate(endDate)
				.withQuoteSymbol(symbol)
				.build();

		final ResponseEntity<String> csv = restTemplate.getForEntity(dividendAndSplitUrl, String.class);
		final InputStream in = IOUtils.toInputStream(csv.getBody(), StandardCharsets.UTF_8);
		final CSVReader reader = new CSVReader(new InputStreamReader(in), ',', '\'', '\\', 1, false);

		List<YahooCsvResult> dividendsAndSplitsFromCsv = Lists.newArrayList();

		try {
			final List<String[]> values = reader.readAll();
			reader.close();
			dividendsAndSplitsFromCsv = acquireDividendsAndSplits(values);
		} catch (IOException | ParseException ex) {
			log.error(ex.getMessage(), ex);
		}
		return dividendsAndSplitsFromCsv;
	}

	private List<YahooCsvResult> acquireDividendsAndSplits(final List<String[]> values) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final List<YahooCsvResult> result = Lists.newArrayList();

		for (String[] element : values) {
			final String type = element[0];
			if (type.equalsIgnoreCase(StockActionType.DIVIDEND.getKey())) {
				result.add(new YahooCsvResult(StockActionType.DIVIDEND, sdf.parse(element[1].trim()), element[2]));
			} else if (type.equalsIgnoreCase(StockActionType.SPLIT.getKey())) {
				result.add(new YahooCsvResult(StockActionType.SPLIT, sdf.parse(element[1].trim()), element[2]));
			}
		}
		return result;
	}

}
