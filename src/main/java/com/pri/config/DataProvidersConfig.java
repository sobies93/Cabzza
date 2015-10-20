package com.pri.config;

import com.pri.dataproviders.yahoo.YahooDataProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Mikolaj Manski
 */
@Configuration
public class DataProvidersConfig {

	@Bean
	public YahooDataProvider getYahooDataProvider() {
		return new YahooDataProvider();
	}
}
