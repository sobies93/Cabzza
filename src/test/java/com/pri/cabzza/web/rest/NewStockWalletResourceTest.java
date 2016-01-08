package com.pri.cabzza.web.rest;

import com.pri.cabzza.Application;
import com.pri.cabzza.domain.NewStockWallet;
import com.pri.cabzza.repository.NewStockWalletRepository;
import com.pri.cabzza.repository.search.NewStockWalletSearchRepository;

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
 * Test class for the NewStockWalletResource REST controller.
 *
 * @see NewStockWalletResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NewStockWalletResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_HISTORICAL_DATA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HISTORICAL_DATA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CALCULATINGS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALCULATINGS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PROGNOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROGNOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_RISKFREE_RATE = 1D;
    private static final Double UPDATED_RISKFREE_RATE = 2D;

    private static final Double DEFAULT_EXPECTED_RETURN = 1D;
    private static final Double UPDATED_EXPECTED_RETURN = 2D;

    private static final Double DEFAULT_EXPECTED_VARIATION = 1D;
    private static final Double UPDATED_EXPECTED_VARIATION = 2D;

    private static final Double DEFAULT_SHARP_RATIO = 1D;
    private static final Double UPDATED_SHARP_RATIO = 2D;

    private static final Boolean DEFAULT_IS_INVESTOR = false;
    private static final Boolean UPDATED_IS_INVESTOR = true;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Double DEFAULT_REAL_VARIATION = 1D;
    private static final Double UPDATED_REAL_VARIATION = 2D;

    private static final Double DEFAULT_REAL_RETURN = 1D;
    private static final Double UPDATED_REAL_RETURN = 2D;

    @Inject
    private NewStockWalletRepository newStockWalletRepository;

    @Inject
    private NewStockWalletSearchRepository newStockWalletSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNewStockWalletMockMvc;

    private NewStockWallet newStockWallet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NewStockWalletResource newStockWalletResource = new NewStockWalletResource();
        ReflectionTestUtils.setField(newStockWalletResource, "newStockWalletRepository", newStockWalletRepository);
        ReflectionTestUtils.setField(newStockWalletResource, "newStockWalletSearchRepository", newStockWalletSearchRepository);
        this.restNewStockWalletMockMvc = MockMvcBuilders.standaloneSetup(newStockWalletResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        newStockWallet = new NewStockWallet();
        newStockWallet.setName(DEFAULT_NAME);
        newStockWallet.setHistoricalDataDate(DEFAULT_HISTORICAL_DATA_DATE);
        newStockWallet.setCalculatingsDate(DEFAULT_CALCULATINGS_DATE);
        newStockWallet.setPrognoseDate(DEFAULT_PROGNOSE_DATE);
        newStockWallet.setRiskfreeRate(DEFAULT_RISKFREE_RATE);
        newStockWallet.setExpectedReturn(DEFAULT_EXPECTED_RETURN);
        newStockWallet.setExpectedVariation(DEFAULT_EXPECTED_VARIATION);
        newStockWallet.setSharpRatio(DEFAULT_SHARP_RATIO);
        newStockWallet.setIsInvestor(DEFAULT_IS_INVESTOR);
        newStockWallet.setDescription(DEFAULT_DESCRIPTION);
        newStockWallet.setRealVariation(DEFAULT_REAL_VARIATION);
        newStockWallet.setRealReturn(DEFAULT_REAL_RETURN);
    }

    @Test
    @Transactional
    public void createNewStockWallet() throws Exception {
        int databaseSizeBeforeCreate = newStockWalletRepository.findAll().size();

        // Create the NewStockWallet

        restNewStockWalletMockMvc.perform(post("/api/newStockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newStockWallet)))
                .andExpect(status().isCreated());

        // Validate the NewStockWallet in the database
        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeCreate + 1);
        NewStockWallet testNewStockWallet = newStockWallets.get(newStockWallets.size() - 1);
        assertThat(testNewStockWallet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNewStockWallet.getHistoricalDataDate()).isEqualTo(DEFAULT_HISTORICAL_DATA_DATE);
        assertThat(testNewStockWallet.getCalculatingsDate()).isEqualTo(DEFAULT_CALCULATINGS_DATE);
        assertThat(testNewStockWallet.getPrognoseDate()).isEqualTo(DEFAULT_PROGNOSE_DATE);
        assertThat(testNewStockWallet.getRiskfreeRate()).isEqualTo(DEFAULT_RISKFREE_RATE);
        assertThat(testNewStockWallet.getExpectedReturn()).isEqualTo(DEFAULT_EXPECTED_RETURN);
        assertThat(testNewStockWallet.getExpectedVariation()).isEqualTo(DEFAULT_EXPECTED_VARIATION);
        assertThat(testNewStockWallet.getSharpRatio()).isEqualTo(DEFAULT_SHARP_RATIO);
        assertThat(testNewStockWallet.getIsInvestor()).isEqualTo(DEFAULT_IS_INVESTOR);
        assertThat(testNewStockWallet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewStockWallet.getRealVariation()).isEqualTo(DEFAULT_REAL_VARIATION);
        assertThat(testNewStockWallet.getRealReturn()).isEqualTo(DEFAULT_REAL_RETURN);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = newStockWalletRepository.findAll().size();
        // set the field null
        newStockWallet.setName(null);

        // Create the NewStockWallet, which fails.

        restNewStockWalletMockMvc.perform(post("/api/newStockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newStockWallet)))
                .andExpect(status().isBadRequest());

        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHistoricalDataDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = newStockWalletRepository.findAll().size();
        // set the field null
        newStockWallet.setHistoricalDataDate(null);

        // Create the NewStockWallet, which fails.

        restNewStockWalletMockMvc.perform(post("/api/newStockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newStockWallet)))
                .andExpect(status().isBadRequest());

        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCalculatingsDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = newStockWalletRepository.findAll().size();
        // set the field null
        newStockWallet.setCalculatingsDate(null);

        // Create the NewStockWallet, which fails.

        restNewStockWalletMockMvc.perform(post("/api/newStockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newStockWallet)))
                .andExpect(status().isBadRequest());

        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNewStockWallets() throws Exception {
        // Initialize the database
        newStockWalletRepository.saveAndFlush(newStockWallet);

        // Get all the newStockWallets
        restNewStockWalletMockMvc.perform(get("/api/newStockWallets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(newStockWallet.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].historicalDataDate").value(hasItem(DEFAULT_HISTORICAL_DATA_DATE.toString())))
                .andExpect(jsonPath("$.[*].calculatingsDate").value(hasItem(DEFAULT_CALCULATINGS_DATE.toString())))
                .andExpect(jsonPath("$.[*].prognoseDate").value(hasItem(DEFAULT_PROGNOSE_DATE.toString())))
                .andExpect(jsonPath("$.[*].riskfreeRate").value(hasItem(DEFAULT_RISKFREE_RATE.doubleValue())))
                .andExpect(jsonPath("$.[*].expectedReturn").value(hasItem(DEFAULT_EXPECTED_RETURN.doubleValue())))
                .andExpect(jsonPath("$.[*].expectedVariation").value(hasItem(DEFAULT_EXPECTED_VARIATION.doubleValue())))
                .andExpect(jsonPath("$.[*].sharpRatio").value(hasItem(DEFAULT_SHARP_RATIO.doubleValue())))
                .andExpect(jsonPath("$.[*].isInvestor").value(hasItem(DEFAULT_IS_INVESTOR.booleanValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].realVariation").value(hasItem(DEFAULT_REAL_VARIATION.doubleValue())))
                .andExpect(jsonPath("$.[*].realReturn").value(hasItem(DEFAULT_REAL_RETURN.doubleValue())));
    }

    @Test
    @Transactional
    public void getNewStockWallet() throws Exception {
        // Initialize the database
        newStockWalletRepository.saveAndFlush(newStockWallet);

        // Get the newStockWallet
        restNewStockWalletMockMvc.perform(get("/api/newStockWallets/{id}", newStockWallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(newStockWallet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.historicalDataDate").value(DEFAULT_HISTORICAL_DATA_DATE.toString()))
            .andExpect(jsonPath("$.calculatingsDate").value(DEFAULT_CALCULATINGS_DATE.toString()))
            .andExpect(jsonPath("$.prognoseDate").value(DEFAULT_PROGNOSE_DATE.toString()))
            .andExpect(jsonPath("$.riskfreeRate").value(DEFAULT_RISKFREE_RATE.doubleValue()))
            .andExpect(jsonPath("$.expectedReturn").value(DEFAULT_EXPECTED_RETURN.doubleValue()))
            .andExpect(jsonPath("$.expectedVariation").value(DEFAULT_EXPECTED_VARIATION.doubleValue()))
            .andExpect(jsonPath("$.sharpRatio").value(DEFAULT_SHARP_RATIO.doubleValue()))
            .andExpect(jsonPath("$.isInvestor").value(DEFAULT_IS_INVESTOR.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.realVariation").value(DEFAULT_REAL_VARIATION.doubleValue()))
            .andExpect(jsonPath("$.realReturn").value(DEFAULT_REAL_RETURN.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNewStockWallet() throws Exception {
        // Get the newStockWallet
        restNewStockWalletMockMvc.perform(get("/api/newStockWallets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewStockWallet() throws Exception {
        // Initialize the database
        newStockWalletRepository.saveAndFlush(newStockWallet);

		int databaseSizeBeforeUpdate = newStockWalletRepository.findAll().size();

        // Update the newStockWallet
        newStockWallet.setName(UPDATED_NAME);
        newStockWallet.setHistoricalDataDate(UPDATED_HISTORICAL_DATA_DATE);
        newStockWallet.setCalculatingsDate(UPDATED_CALCULATINGS_DATE);
        newStockWallet.setPrognoseDate(UPDATED_PROGNOSE_DATE);
        newStockWallet.setRiskfreeRate(UPDATED_RISKFREE_RATE);
        newStockWallet.setExpectedReturn(UPDATED_EXPECTED_RETURN);
        newStockWallet.setExpectedVariation(UPDATED_EXPECTED_VARIATION);
        newStockWallet.setSharpRatio(UPDATED_SHARP_RATIO);
        newStockWallet.setIsInvestor(UPDATED_IS_INVESTOR);
        newStockWallet.setDescription(UPDATED_DESCRIPTION);
        newStockWallet.setRealVariation(UPDATED_REAL_VARIATION);
        newStockWallet.setRealReturn(UPDATED_REAL_RETURN);

        restNewStockWalletMockMvc.perform(put("/api/newStockWallets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newStockWallet)))
                .andExpect(status().isOk());

        // Validate the NewStockWallet in the database
        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeUpdate);
        NewStockWallet testNewStockWallet = newStockWallets.get(newStockWallets.size() - 1);
        assertThat(testNewStockWallet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNewStockWallet.getHistoricalDataDate()).isEqualTo(UPDATED_HISTORICAL_DATA_DATE);
        assertThat(testNewStockWallet.getCalculatingsDate()).isEqualTo(UPDATED_CALCULATINGS_DATE);
        assertThat(testNewStockWallet.getPrognoseDate()).isEqualTo(UPDATED_PROGNOSE_DATE);
        assertThat(testNewStockWallet.getRiskfreeRate()).isEqualTo(UPDATED_RISKFREE_RATE);
        assertThat(testNewStockWallet.getExpectedReturn()).isEqualTo(UPDATED_EXPECTED_RETURN);
        assertThat(testNewStockWallet.getExpectedVariation()).isEqualTo(UPDATED_EXPECTED_VARIATION);
        assertThat(testNewStockWallet.getSharpRatio()).isEqualTo(UPDATED_SHARP_RATIO);
        assertThat(testNewStockWallet.getIsInvestor()).isEqualTo(UPDATED_IS_INVESTOR);
        assertThat(testNewStockWallet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewStockWallet.getRealVariation()).isEqualTo(UPDATED_REAL_VARIATION);
        assertThat(testNewStockWallet.getRealReturn()).isEqualTo(UPDATED_REAL_RETURN);
    }

    @Test
    @Transactional
    public void deleteNewStockWallet() throws Exception {
        // Initialize the database
        newStockWalletRepository.saveAndFlush(newStockWallet);

		int databaseSizeBeforeDelete = newStockWalletRepository.findAll().size();

        // Get the newStockWallet
        restNewStockWalletMockMvc.perform(delete("/api/newStockWallets/{id}", newStockWallet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NewStockWallet> newStockWallets = newStockWalletRepository.findAll();
        assertThat(newStockWallets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
