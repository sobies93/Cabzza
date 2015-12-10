package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pri.cabzza.domain.StockQuotes;
import com.pri.cabzza.repository.StockQuotesRepository;
import com.pri.cabzza.repository.search.StockQuotesSearchRepository;
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
 * REST controller for managing StockQuotes.
 */
@RestController
@RequestMapping("/api")
public class StockQuotesResource {

    private final Logger log = LoggerFactory.getLogger(StockQuotesResource.class);

    @Inject
    private StockQuotesRepository stockQuotesRepository;

    @Inject
    private StockQuotesSearchRepository stockQuotesSearchRepository;

    /**
     * POST  /stockQuotess -> Create a new stockQuotes.
     */
    @RequestMapping(value = "/stockQuotess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockQuotes> createStockQuotes(@RequestBody StockQuotes stockQuotes) throws URISyntaxException {
        log.debug("REST request to save StockQuotes : {}", stockQuotes);
        if (stockQuotes.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new stockQuotes cannot already have an ID").body(null);
        }
        StockQuotes result = stockQuotesRepository.save(stockQuotes);
        stockQuotesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stockQuotess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockQuotes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stockQuotess -> Updates an existing stockQuotes.
     */
    @RequestMapping(value = "/stockQuotess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockQuotes> updateStockQuotes(@RequestBody StockQuotes stockQuotes) throws URISyntaxException {
        log.debug("REST request to update StockQuotes : {}", stockQuotes);
        if (stockQuotes.getId() == null) {
            return createStockQuotes(stockQuotes);
        }
        StockQuotes result = stockQuotesRepository.save(stockQuotes);
        stockQuotesSearchRepository.save(stockQuotes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockQuotes", stockQuotes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stockQuotess -> get all the stockQuotess.
     */
    @RequestMapping(value = "/stockQuotess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockQuotes> getAllStockQuotess() {
        log.debug("REST request to get all StockQuotess");
        return stockQuotesRepository.findAll();
    }

    /**
     * GET  /stockQuotess/:id -> get the "id" stockQuotes.
     */
    @RequestMapping(value = "/stockQuotess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockQuotes> getStockQuotes(@PathVariable Long id) {
        log.debug("REST request to get StockQuotes : {}", id);
        return Optional.ofNullable(stockQuotesRepository.findOne(id))
            .map(stockQuotes -> new ResponseEntity<>(
                stockQuotes,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stockQuotess/:id -> delete the "id" stockQuotes.
     */
    @RequestMapping(value = "/stockQuotess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockQuotes(@PathVariable Long id) {
        log.debug("REST request to delete StockQuotes : {}", id);
        stockQuotesRepository.delete(id);
        stockQuotesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockQuotes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stockQuotess/:query -> search for the stockQuotes corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stockQuotess/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockQuotes> searchStockQuotess(@PathVariable String query) {
        return StreamSupport
            .stream(stockQuotesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
