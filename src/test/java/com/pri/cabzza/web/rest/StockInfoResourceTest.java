package com.pri.cabzza.web.rest;

import com.pri.cabzza.Application;
import com.pri.cabzza.domain.StockInfo;
import com.pri.cabzza.repository.StockInfoRepository;
import com.pri.cabzza.repository.search.StockInfoSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StockInfoResource REST controller.
 *
 * @see StockInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockInfoResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SYMBOL = "AAAAA";
    private static final String UPDATED_SYMBOL = "BBBBB";

    private static final LocalDate DEFAULT_QUOTES_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTES_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_QUOTES_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTES_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_INVESTOR_MODE_AVAIABLE = false;
    private static final Boolean UPDATED_IS_INVESTOR_MODE_AVAIABLE = true;

    @Inject
    private StockInfoRepository stockInfoRepository;

    @Inject
    private StockInfoSearchRepository stockInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockInfoMockMvc;

    private StockInfo stockInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockInfoResource stockInfoResource = new StockInfoResource();
        ReflectionTestUtils.setField(stockInfoResource, "stockInfoRepository", stockInfoRepository);
        ReflectionTestUtils.setField(stockInfoResource, "stockInfoSearchRepository", stockInfoSearchRepository);
        this.restStockInfoMockMvc = MockMvcBuilders.standaloneSetup(stockInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockInfo = new StockInfo();
        stockInfo.setName(DEFAULT_NAME);
        stockInfo.setSymbol(DEFAULT_SYMBOL);
        stockInfo.setQuotesStartDate(DEFAULT_QUOTES_START_DATE);
        stockInfo.setQuotesEndDate(DEFAULT_QUOTES_END_DATE);
        stockInfo.setIsInvestorModeAvaiable(DEFAULT_IS_INVESTOR_MODE_AVAIABLE);
    }

    @Test
    @Transactional
    public void createStockInfo() throws Exception {
        int databaseSizeBeforeCreate = stockInfoRepository.findAll().size();

        // Create the StockInfo

        restStockInfoMockMvc.perform(post("/api/stockInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockInfo)))
                .andExpect(status().isCreated());

        // Validate the StockInfo in the database
        List<StockInfo> stockInfos = stockInfoRepository.findAll();
        assertThat(stockInfos).hasSize(databaseSizeBeforeCreate + 1);
        StockInfo testStockInfo = stockInfos.get(stockInfos.size() - 1);
        assertThat(testStockInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockInfo.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testStockInfo.getQuotesStartDate()).isEqualTo(DEFAULT_QUOTES_START_DATE);
        assertThat(testStockInfo.getQuotesEndDate()).isEqualTo(DEFAULT_QUOTES_END_DATE);
        assertThat(testStockInfo.getIsInvestorModeAvaiable()).isEqualTo(DEFAULT_IS_INVESTOR_MODE_AVAIABLE);
    }

    @Test
    @Transactional
    public void getAllStockInfos() throws Exception {
        // Initialize the database
        stockInfoRepository.saveAndFlush(stockInfo);

        // Get all the stockInfos
        restStockInfoMockMvc.perform(get("/api/stockInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
                .andExpect(jsonPath("$.[*].quotesStartDate").value(hasItem(DEFAULT_QUOTES_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].quotesEndDate").value(hasItem(DEFAULT_QUOTES_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].isInvestorModeAvaiable").value(hasItem(DEFAULT_IS_INVESTOR_MODE_AVAIABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getStockInfo() throws Exception {
        // Initialize the database
        stockInfoRepository.saveAndFlush(stockInfo);

        // Get the stockInfo
        restStockInfoMockMvc.perform(get("/api/stockInfos/{id}", stockInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.quotesStartDate").value(DEFAULT_QUOTES_START_DATE.toString()))
            .andExpect(jsonPath("$.quotesEndDate").value(DEFAULT_QUOTES_END_DATE.toString()))
            .andExpect(jsonPath("$.isInvestorModeAvaiable").value(DEFAULT_IS_INVESTOR_MODE_AVAIABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockInfo() throws Exception {
        // Get the stockInfo
        restStockInfoMockMvc.perform(get("/api/stockInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockInfo() throws Exception {
        // Initialize the database
        stockInfoRepository.saveAndFlush(stockInfo);

		int databaseSizeBeforeUpdate = stockInfoRepository.findAll().size();

        // Update the stockInfo
        stockInfo.setName(UPDATED_NAME);
        stockInfo.setSymbol(UPDATED_SYMBOL);
        stockInfo.setQuotesStartDate(UPDATED_QUOTES_START_DATE);
        stockInfo.setQuotesEndDate(UPDATED_QUOTES_END_DATE);
        stockInfo.setIsInvestorModeAvaiable(UPDATED_IS_INVESTOR_MODE_AVAIABLE);

        restStockInfoMockMvc.perform(put("/api/stockInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockInfo)))
                .andExpect(status().isOk());

        // Validate the StockInfo in the database
        List<StockInfo> stockInfos = stockInfoRepository.findAll();
        assertThat(stockInfos).hasSize(databaseSizeBeforeUpdate);
        StockInfo testStockInfo = stockInfos.get(stockInfos.size() - 1);
        assertThat(testStockInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockInfo.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testStockInfo.getQuotesStartDate()).isEqualTo(UPDATED_QUOTES_START_DATE);
        assertThat(testStockInfo.getQuotesEndDate()).isEqualTo(UPDATED_QUOTES_END_DATE);
        assertThat(testStockInfo.getIsInvestorModeAvaiable()).isEqualTo(UPDATED_IS_INVESTOR_MODE_AVAIABLE);
    }

    @Test
    @Transactional
    public void deleteStockInfo() throws Exception {
        // Initialize the database
        stockInfoRepository.saveAndFlush(stockInfo);

		int databaseSizeBeforeDelete = stockInfoRepository.findAll().size();

        // Get the stockInfo
        restStockInfoMockMvc.perform(delete("/api/stockInfos/{id}", stockInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockInfo> stockInfos = stockInfoRepository.findAll();
        assertThat(stockInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
