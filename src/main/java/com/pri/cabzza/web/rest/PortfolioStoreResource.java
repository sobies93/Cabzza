package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pri.cabzza.domain.PortfolioStore;
import com.pri.cabzza.repository.NewStockWalletRepository;
import com.pri.cabzza.repository.PortfolioStoreRepository;
import com.pri.cabzza.repository.search.PortfolioStoreSearchRepository;
import com.pri.cabzza.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PortfolioStore.
 */
@RestController
@RequestMapping("/api")
public class PortfolioStoreResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioStoreResource.class);

    @Inject
    private PortfolioStoreRepository portfolioStoreRepository;

    @Inject
    private NewStockWalletRepository newStockWalletRepository;

    @Inject
    private PortfolioStoreSearchRepository portfolioStoreSearchRepository;

    /**
     * POST  /portfolioStores -> Create a new portfolioStore.
     */
    @RequestMapping(value = "/portfolioStores",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortfolioStore> createPortfolioStore(@RequestBody PortfolioStore portfolioStore) throws URISyntaxException {
        log.debug("REST request to save PortfolioStore : {}", portfolioStore);
        if (portfolioStore.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new portfolioStore cannot already have an ID").body(null);
        }
        PortfolioStore result = portfolioStoreRepository.save(portfolioStore);
        portfolioStoreSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/portfolioStores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("portfolioStore", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portfolioStores -> Updates an existing portfolioStore.
     */
    @RequestMapping(value = "/portfolioStores",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortfolioStore> updatePortfolioStore(@RequestBody PortfolioStore portfolioStore) throws URISyntaxException {
        log.debug("REST request to update PortfolioStore : {}", portfolioStore);
        if (portfolioStore.getId() == null) {
            return createPortfolioStore(portfolioStore);
        }
        PortfolioStore result = portfolioStoreRepository.save(portfolioStore);
        portfolioStoreSearchRepository.save(portfolioStore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("portfolioStore", portfolioStore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portfolioStores -> get all the portfolioStores.
     */
    @RequestMapping(value = "/portfolioStores",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PortfolioStore> getAllPortfolioStores() {
        log.debug("REST request to get all PortfolioStores");
        return portfolioStoreRepository.findAll();
    }

    /**
     * GET  portfolioStoresByWallet/:id > get portfolioStores by Wallet id.
     */
    @RequestMapping(value = "/portfolioStoresByWallet/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PortfolioStore> getAllByWallet(@PathVariable Long id) {
        log.debug("REST request to get all PortfolioStores by Wallet");
        return portfolioStoreRepository.findAllByNewStockWallet(newStockWalletRepository.findOne(id));
    }

    /**
     * GET  /portfolioStores/:id -> get the "id" portfolioStore.
     */
    @RequestMapping(value = "/portfolioStores/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortfolioStore> getPortfolioStore(@PathVariable Long id) {
        log.debug("REST request to get PortfolioStore : {}", id);
        return Optional.ofNullable(portfolioStoreRepository.findOne(id))
            .map(portfolioStore -> new ResponseEntity<>(
                portfolioStore,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /portfolioStores/:id -> delete the "id" portfolioStore.
     */
    @RequestMapping(value = "/portfolioStores/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePortfolioStore(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioStore : {}", id);
        portfolioStoreRepository.delete(id);
        portfolioStoreSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("portfolioStore", id.toString())).build();
    }

    /**
     * SEARCH  /_search/portfolioStores/:query -> search for the portfolioStore corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/portfolioStores/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PortfolioStore> searchPortfolioStores(@PathVariable String query) {
        return StreamSupport
            .stream(portfolioStoreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
