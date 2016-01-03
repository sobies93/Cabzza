package com.pri.cabzza.web.rest;

import com.pri.cabzza.Application;
import com.pri.cabzza.domain.StockQuotes;
import com.pri.cabzza.repository.StockQuotesRepository;
import com.pri.cabzza.repository.search.StockQuotesSearchRepository;

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
 * Test class for the StockQuotesResource REST controller.
 *
 * @see StockQuotesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockQuotesResourceTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Double DEFAULT_SPLIT_RATE = 1D;
    private static final Double UPDATED_SPLIT_RATE = 2D;

    private static final Double DEFAULT_DIVIDEND = 1D;
    private static final Double UPDATED_DIVIDEND = 2D;

    @Inject
    private StockQuotesRepository stockQuotesRepository;

    @Inject
    private StockQuotesSearchRepository stockQuotesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockQuotesMockMvc;

    private StockQuotes stockQuotes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockQuotesResource stockQuotesResource = new StockQuotesResource();
        ReflectionTestUtils.setField(stockQuotesResource, "stockQuotesRepository", stockQuotesRepository);
        ReflectionTestUtils.setField(stockQuotesResource, "stockQuotesSearchRepository", stockQuotesSearchRepository);
        this.restStockQuotesMockMvc = MockMvcBuilders.standaloneSetup(stockQuotesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockQuotes = new StockQuotes();
        stockQuotes.setDate(DEFAULT_DATE);
        stockQuotes.setValue(DEFAULT_VALUE);
        stockQuotes.setSplitRate(DEFAULT_SPLIT_RATE);
        stockQuotes.setDividend(DEFAULT_DIVIDEND);
    }

    @Test
    @Transactional
    public void createStockQuotes() throws Exception {
        int databaseSizeBeforeCreate = stockQuotesRepository.findAll().size();

        // Create the StockQuotes

        restStockQuotesMockMvc.perform(post("/api/stockQuotess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockQuotes)))
                .andExpect(status().isCreated());

        // Validate the StockQuotes in the database
        List<StockQuotes> stockQuotess = stockQuotesRepository.findAll();
        assertThat(stockQuotess).hasSize(databaseSizeBeforeCreate + 1);
        StockQuotes testStockQuotes = stockQuotess.get(stockQuotess.size() - 1);
        assertThat(testStockQuotes.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStockQuotes.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStockQuotes.getSplitRate()).isEqualTo(DEFAULT_SPLIT_RATE);
        assertThat(testStockQuotes.getDividend()).isEqualTo(DEFAULT_DIVIDEND);
    }

    @Test
    @Transactional
    public void getAllStockQuotess() throws Exception {
        // Initialize the database
        stockQuotesRepository.saveAndFlush(stockQuotes);

        // Get all the stockQuotess
        restStockQuotesMockMvc.perform(get("/api/stockQuotess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockQuotes.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
                .andExpect(jsonPath("$.[*].splitRate").value(hasItem(DEFAULT_SPLIT_RATE.doubleValue())))
                .andExpect(jsonPath("$.[*].dividend").value(hasItem(DEFAULT_DIVIDEND.doubleValue())));
    }

    @Test
    @Transactional
    public void getStockQuotes() throws Exception {
        // Initialize the database
        stockQuotesRepository.saveAndFlush(stockQuotes);

        // Get the stockQuotes
        restStockQuotesMockMvc.perform(get("/api/stockQuotess/{id}", stockQuotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockQuotes.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.splitRate").value(DEFAULT_SPLIT_RATE.doubleValue()))
            .andExpect(jsonPath("$.dividend").value(DEFAULT_DIVIDEND.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockQuotes() throws Exception {
        // Get the stockQuotes
        restStockQuotesMockMvc.perform(get("/api/stockQuotess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockQuotes() throws Exception {
        // Initialize the database
        stockQuotesRepository.saveAndFlush(stockQuotes);

		int databaseSizeBeforeUpdate = stockQuotesRepository.findAll().size();

        // Update the stockQuotes
        stockQuotes.setDate(UPDATED_DATE);
        stockQuotes.setValue(UPDATED_VALUE);
        stockQuotes.setSplitRate(UPDATED_SPLIT_RATE);
        stockQuotes.setDividend(UPDATED_DIVIDEND);

        restStockQuotesMockMvc.perform(put("/api/stockQuotess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockQuotes)))
                .andExpect(status().isOk());

        // Validate the StockQuotes in the database
        List<StockQuotes> stockQuotess = stockQuotesRepository.findAll();
        assertThat(stockQuotess).hasSize(databaseSizeBeforeUpdate);
        StockQuotes testStockQuotes = stockQuotess.get(stockQuotess.size() - 1);
        assertThat(testStockQuotes.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStockQuotes.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStockQuotes.getSplitRate()).isEqualTo(UPDATED_SPLIT_RATE);
        assertThat(testStockQuotes.getDividend()).isEqualTo(UPDATED_DIVIDEND);
    }

    @Test
    @Transactional
    public void deleteStockQuotes() throws Exception {
        // Initialize the database
        stockQuotesRepository.saveAndFlush(stockQuotes);

		int databaseSizeBeforeDelete = stockQuotesRepository.findAll().size();

        // Get the stockQuotes
        restStockQuotesMockMvc.perform(delete("/api/stockQuotess/{id}", stockQuotes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockQuotes> stockQuotess = stockQuotesRepository.findAll();
        assertThat(stockQuotess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
