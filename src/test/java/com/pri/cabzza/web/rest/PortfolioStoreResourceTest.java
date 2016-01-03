package com.pri.cabzza.web.rest;

import com.pri.cabzza.Application;
import com.pri.cabzza.domain.PortfolioStore;
import com.pri.cabzza.repository.PortfolioStoreRepository;
import com.pri.cabzza.repository.search.PortfolioStoreSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PortfolioStoreResource REST controller.
 *
 * @see PortfolioStoreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PortfolioStoreResourceTest {


    private static final Double DEFAULT_PERCENT = 1D;
    private static final Double UPDATED_PERCENT = 2D;

    @Inject
    private PortfolioStoreRepository portfolioStoreRepository;

    @Inject
    private PortfolioStoreSearchRepository portfolioStoreSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPortfolioStoreMockMvc;

    private PortfolioStore portfolioStore;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PortfolioStoreResource portfolioStoreResource = new PortfolioStoreResource();
        ReflectionTestUtils.setField(portfolioStoreResource, "portfolioStoreRepository", portfolioStoreRepository);
        ReflectionTestUtils.setField(portfolioStoreResource, "portfolioStoreSearchRepository", portfolioStoreSearchRepository);
        this.restPortfolioStoreMockMvc = MockMvcBuilders.standaloneSetup(portfolioStoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        portfolioStore = new PortfolioStore();
        portfolioStore.setPercent(DEFAULT_PERCENT);
    }

    @Test
    @Transactional
    public void createPortfolioStore() throws Exception {
        int databaseSizeBeforeCreate = portfolioStoreRepository.findAll().size();

        // Create the PortfolioStore

        restPortfolioStoreMockMvc.perform(post("/api/portfolioStores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolioStore)))
                .andExpect(status().isCreated());

        // Validate the PortfolioStore in the database
        List<PortfolioStore> portfolioStores = portfolioStoreRepository.findAll();
        assertThat(portfolioStores).hasSize(databaseSizeBeforeCreate + 1);
        PortfolioStore testPortfolioStore = portfolioStores.get(portfolioStores.size() - 1);
        assertThat(testPortfolioStore.getPercent()).isEqualTo(DEFAULT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPortfolioStores() throws Exception {
        // Initialize the database
        portfolioStoreRepository.saveAndFlush(portfolioStore);

        // Get all the portfolioStores
        restPortfolioStoreMockMvc.perform(get("/api/portfolioStores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(portfolioStore.getId().intValue())))
                .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT.doubleValue())));
    }

    @Test
    @Transactional
    public void getPortfolioStore() throws Exception {
        // Initialize the database
        portfolioStoreRepository.saveAndFlush(portfolioStore);

        // Get the portfolioStore
        restPortfolioStoreMockMvc.perform(get("/api/portfolioStores/{id}", portfolioStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(portfolioStore.getId().intValue()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPortfolioStore() throws Exception {
        // Get the portfolioStore
        restPortfolioStoreMockMvc.perform(get("/api/portfolioStores/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortfolioStore() throws Exception {
        // Initialize the database
        portfolioStoreRepository.saveAndFlush(portfolioStore);

		int databaseSizeBeforeUpdate = portfolioStoreRepository.findAll().size();

        // Update the portfolioStore
        portfolioStore.setPercent(UPDATED_PERCENT);

        restPortfolioStoreMockMvc.perform(put("/api/portfolioStores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolioStore)))
                .andExpect(status().isOk());

        // Validate the PortfolioStore in the database
        List<PortfolioStore> portfolioStores = portfolioStoreRepository.findAll();
        assertThat(portfolioStores).hasSize(databaseSizeBeforeUpdate);
        PortfolioStore testPortfolioStore = portfolioStores.get(portfolioStores.size() - 1);
        assertThat(testPortfolioStore.getPercent()).isEqualTo(UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void deletePortfolioStore() throws Exception {
        // Initialize the database
        portfolioStoreRepository.saveAndFlush(portfolioStore);

		int databaseSizeBeforeDelete = portfolioStoreRepository.findAll().size();

        // Get the portfolioStore
        restPortfolioStoreMockMvc.perform(delete("/api/portfolioStores/{id}", portfolioStore.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PortfolioStore> portfolioStores = portfolioStoreRepository.findAll();
        assertThat(portfolioStores).hasSize(databaseSizeBeforeDelete - 1);
    }
}
