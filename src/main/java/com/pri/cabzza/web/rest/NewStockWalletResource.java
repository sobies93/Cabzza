package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pri.cabzza.domain.NewStockWallet;
import com.pri.cabzza.domain.PortfolioStore;
import com.pri.cabzza.repository.NewStockWalletRepository;
import com.pri.cabzza.repository.PortfolioStoreRepository;
import com.pri.cabzza.repository.search.NewStockWalletSearchRepository;
import com.pri.cabzza.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing NewStockWallet.
 */
@RestController
@RequestMapping("/api")
public class NewStockWalletResource {

    private final Logger log = LoggerFactory.getLogger(NewStockWalletResource.class);

    @Inject
    private NewStockWalletRepository newStockWalletRepository;

    @Inject
    private PortfolioStoreRepository portfolioStoreRepository;

    @Inject
    private NewStockWalletSearchRepository newStockWalletSearchRepository;

    /**
     * POST  /newStockWallets -> Create a new newStockWallet.
     */
    @RequestMapping(value = "/newStockWallets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NewStockWallet> createNewStockWallet(@Valid @RequestBody NewStockWallet newStockWallet) throws URISyntaxException {
        log.debug("REST request to save NewStockWallet : {}", newStockWallet);
        if (newStockWallet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new newStockWallet cannot already have an ID").body(null);
        }
        NewStockWallet result = newStockWalletRepository.save(newStockWallet);
        newStockWalletSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/newStockWallets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("newStockWallet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /newStockWallets -> Updates an existing newStockWallet.
     */
    @RequestMapping(value = "/newStockWallets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NewStockWallet> updateNewStockWallet(@Valid @RequestBody NewStockWallet newStockWallet) throws URISyntaxException {
        log.debug("REST request to update NewStockWallet : {}", newStockWallet);
        if (newStockWallet.getId() == null) {
            return createNewStockWallet(newStockWallet);
        }
        NewStockWallet result = newStockWalletRepository.save(newStockWallet);
        newStockWalletSearchRepository.save(newStockWallet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("newStockWallet", newStockWallet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /newStockWallets -> get all the newStockWallets.
     */
    @RequestMapping(value = "/newStockWallets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NewStockWallet> getAllNewStockWallets() {
        log.debug("REST request to get all NewStockWallets");
        return newStockWalletRepository.findAll();
    }

    /**
     * GET  /usersNewStockWallets -> get users newStockWallets.
     */
    @RequestMapping(value = "/usersNewStockWallets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NewStockWallet> getUsersNewStockWallets() {
        log.debug("REST request to get all NewStockWallets");
        return newStockWalletRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  /newStockWallets/:id -> get the "id" newStockWallet.
     */
    @RequestMapping(value = "/newStockWallets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NewStockWallet> getNewStockWallet(@PathVariable Long id) {
        log.debug("REST request to get NewStockWallet : {}", id);
        return Optional.ofNullable(newStockWalletRepository.findOne(id))
            .map(newStockWallet -> new ResponseEntity<>(
                newStockWallet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /newStockWallets/:id -> delete the "id" newStockWallet.
     */
    @RequestMapping(value = "/newStockWallets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNewStockWallet(@PathVariable Long id) {
        log.debug("REST request to delete NewStockWallet : {}", id);
        newStockWalletRepository.delete(id);
        newStockWalletSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("newStockWallet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/newStockWallets/:query -> search for the newStockWallet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/newStockWallets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NewStockWallet> searchNewStockWallets(@PathVariable String query) {
        return StreamSupport
            .stream(newStockWalletSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
