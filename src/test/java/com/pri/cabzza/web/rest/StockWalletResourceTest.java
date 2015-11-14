package com.pri.cabzza.web.rest;

import com.pri.cabzza.Application;
import com.pri.cabzza.domain.StockWallet;
import com.pri.cabzza.repository.StockWalletRepository;
import com.pri.cabzza.repository.search.StockWalletSearchRepository;
import com.pri.cabzza.web.rest.dto.StockWalletDTO;
import com.pri.cabzza.web.rest.mapper.StockWalletMapper;

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
 * Test class for the StockWalletResource REST controller.
 *
 * @see StockWalletResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StockWalletResourceTest {

    private static final String DEFAULT_QUOTE_SYMBOL = "AAAAA";
    private static final String UPDATED_QUOTE_SYMBOL = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private StockWalletRepository stockWalletRepository;

    @Inject
    private StockWalletMapper stockWalletMapper;

    @Inject
    private StockWalletSearchRepository stockWalletSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStockWalletMockMvc;

    private StockWallet stockWallet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StockWalletResource stockWalletResource = new StockWalletResource();
        ReflectionTestUtils.setField(stockWalletResource, "stockWalletRepository", stockWalletRepository);
        ReflectionTestUtils.setField(stockWalletResource, "stockWalletMapper", stockWalletMapper);
        ReflectionTestUtils.setField(stockWalletResource, "stockWalletSearchRepository", stockWalletSearchRepository);
        this.restStockWalletMockMvc = MockMvcBuilders.standaloneSetup(stockWalletResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stockWallet = new StockWallet();
        stockWallet.setQuoteSymbols(DEFAULT_QUOTE_SYMBOL);
        stockWallet.setStartDate(DEFAULT_START_DATE);
        stockWallet.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createStockWallet() throws Exception {
        int databaseSizeBeforeCreate = stockWalletRepository.findAll().size();

        // Create the StockWallet
        StockWalletDTO stockWalletDTO = stockWalletMapper.stockWalletToStockWalletDTO(stockWallet);

        restStockWalletMockMvc.perform(post("/api/stockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockWalletDTO)))
                .andExpect(status().isCreated());

        // Validate the StockWallet in the database
        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeCreate + 1);
        StockWallet testStockWallet = stockWallets.get(stockWallets.size() - 1);
        assertThat(testStockWallet.getQuoteSymbols()).isEqualTo(DEFAULT_QUOTE_SYMBOL);
        assertThat(testStockWallet.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStockWallet.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void checkQuoteSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockWalletRepository.findAll().size();
        // set the field null
        stockWallet.setQuoteSymbols(null);

        // Create the StockWallet, which fails.
        StockWalletDTO stockWalletDTO = stockWalletMapper.stockWalletToStockWalletDTO(stockWallet);

        restStockWalletMockMvc.perform(post("/api/stockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockWalletDTO)))
                .andExpect(status().isBadRequest());

        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockWalletRepository.findAll().size();
        // set the field null
        stockWallet.setStartDate(null);

        // Create the StockWallet, which fails.
        StockWalletDTO stockWalletDTO = stockWalletMapper.stockWalletToStockWalletDTO(stockWallet);

        restStockWalletMockMvc.perform(post("/api/stockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockWalletDTO)))
                .andExpect(status().isBadRequest());

        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockWalletRepository.findAll().size();
        // set the field null
        stockWallet.setEndDate(null);

        // Create the StockWallet, which fails.
        StockWalletDTO stockWalletDTO = stockWalletMapper.stockWalletToStockWalletDTO(stockWallet);

        restStockWalletMockMvc.perform(post("/api/stockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockWalletDTO)))
                .andExpect(status().isBadRequest());

        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockWallets() throws Exception {
        // Initialize the database
        stockWalletRepository.saveAndFlush(stockWallet);

        // Get all the stockWallets
        restStockWalletMockMvc.perform(get("/api/stockWallets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stockWallet.getId().intValue())))
                .andExpect(jsonPath("$.[*].quoteSymbol").value(hasItem(DEFAULT_QUOTE_SYMBOL)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStockWallet() throws Exception {
        // Initialize the database
        stockWalletRepository.saveAndFlush(stockWallet);

        // Get the stockWallet
        restStockWalletMockMvc.perform(get("/api/stockWallets/{id}", stockWallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stockWallet.getId().intValue()))
            .andExpect(jsonPath("$.quoteSymbol").value(DEFAULT_QUOTE_SYMBOL))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockWallet() throws Exception {
        // Get the stockWallet
        restStockWalletMockMvc.perform(get("/api/stockWallets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockWallet() throws Exception {
        // Initialize the database
        stockWalletRepository.saveAndFlush(stockWallet);

		int databaseSizeBeforeUpdate = stockWalletRepository.findAll().size();

        // Update the stockWallet
        stockWallet.setQuoteSymbols(UPDATED_QUOTE_SYMBOL);
        stockWallet.setStartDate(UPDATED_START_DATE);
        stockWallet.setEndDate(UPDATED_END_DATE);
        StockWalletDTO stockWalletDTO = stockWalletMapper.stockWalletToStockWalletDTO(stockWallet);

        restStockWalletMockMvc.perform(put("/api/stockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stockWalletDTO)))
                .andExpect(status().isOk());

        // Validate the StockWallet in the database
        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeUpdate);
        StockWallet testStockWallet = stockWallets.get(stockWallets.size() - 1);
        assertThat(testStockWallet.getQuoteSymbols()).isEqualTo(UPDATED_QUOTE_SYMBOL);
        assertThat(testStockWallet.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStockWallet.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteStockWallet() throws Exception {
        // Initialize the database
        stockWalletRepository.saveAndFlush(stockWallet);

		int databaseSizeBeforeDelete = stockWalletRepository.findAll().size();

        // Get the stockWallet
        restStockWalletMockMvc.perform(delete("/api/stockWallets/{id}", stockWallet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StockWallet> stockWallets = stockWalletRepository.findAll();
        assertThat(stockWallets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
