package com.pri.cabzza.dataproviders.yahoo;

import com.pri.cabzza.dataproviders.yahoo.model.StockNameLookupResult;
import com.pri.cabzza.dataproviders.yahoo.model.StockNameLookupResultSet;
import org.elasticsearch.common.collect.ImmutableList;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author rasgrass
 */
public class YahooStockNameLookupProvider {

	private static final String URL_PATTERN = "https://s.yimg.com/aq/autoc?query=%s&region=CA&lang=en-CA&address=sda&sensor=false";

	private final RestTemplate restTemplate;
	
	public YahooStockNameLookupProvider() {
		this.restTemplate = new RestTemplate();
		final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("*", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("*", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
	}

	public StockNameLookupResultSet getLookupResult(String query) {
		final ResponseEntity<StockNameLookupResultSet> json = restTemplate.getForEntity(String.format(URL_PATTERN, query), StockNameLookupResultSet.class);
		return json.getBody();
	}
}
