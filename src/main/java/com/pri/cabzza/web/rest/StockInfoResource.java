package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pri.cabzza.domain.StockInfo;
import com.pri.cabzza.repository.StockInfoRepository;
import com.pri.cabzza.repository.search.StockInfoSearchRepository;
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
 * REST controller for managing StockInfo.
 */
@RestController
@RequestMapping("/api")
public class StockInfoResource {

    private final Logger log = LoggerFactory.getLogger(StockInfoResource.class);

    @Inject
    private StockInfoRepository stockInfoRepository;

    @Inject
    private StockInfoSearchRepository stockInfoSearchRepository;

    /**
     * POST  /stockInfos -> Create a new stockInfo.
     */
    @RequestMapping(value = "/stockInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockInfo> createStockInfo(@RequestBody StockInfo stockInfo) throws URISyntaxException {
        log.debug("REST request to save StockInfo : {}", stockInfo);
        if (stockInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new stockInfo cannot already have an ID").body(null);
        }
        StockInfo result = stockInfoRepository.save(stockInfo);
        stockInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stockInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stockInfos -> Updates an existing stockInfo.
     */
    @RequestMapping(value = "/stockInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockInfo> updateStockInfo(@RequestBody StockInfo stockInfo) throws URISyntaxException {
        log.debug("REST request to update StockInfo : {}", stockInfo);
        if (stockInfo.getId() == null) {
            return createStockInfo(stockInfo);
        }
        StockInfo result = stockInfoRepository.save(stockInfo);
        stockInfoSearchRepository.save(stockInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockInfo", stockInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stockInfos -> get all the stockInfos.
     */
    @RequestMapping(value = "/stockInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockInfo> getAllStockInfos() {
        log.debug("REST request to get all StockInfos");
        return stockInfoRepository.findAll();
    }

    /**
     * GET  /stockInfosByMode -> get stockInfos.
     */
    @RequestMapping(value = "/stockInfosByMode/{mode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockInfo> getAllStockInfosByMode(@PathVariable String mode) {
        log.debug("REST request to get StockInfos By mode");
        if(mode.equals("investor")) {
            return stockInfoRepository.findAllByMode(true);
        } else {
            return stockInfoRepository.findAll();
        }
    }

    /**
     * GET  /stockInfos/:id -> get the "id" stockInfo.
     */
    @RequestMapping(value = "/stockInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockInfo> getStockInfo(@PathVariable Long id) {
        log.debug("REST request to get StockInfo : {}", id);
        return Optional.ofNullable(stockInfoRepository.findOne(id))
            .map(stockInfo -> new ResponseEntity<>(
                stockInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stockInfos/:id -> delete the "id" stockInfo.
     */
    @RequestMapping(value = "/stockInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockInfo(@PathVariable Long id) {
        log.debug("REST request to delete StockInfo : {}", id);
        stockInfoRepository.delete(id);
        stockInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stockInfos/:query -> search for the stockInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stockInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockInfo> searchStockInfos(@PathVariable String query) {
        return StreamSupport
            .stream(stockInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
