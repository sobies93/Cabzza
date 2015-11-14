package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pri.cabzza.domain.StockWallet;
import com.pri.cabzza.repository.StockWalletRepository;
import com.pri.cabzza.repository.search.StockWalletSearchRepository;
import com.pri.cabzza.web.rest.util.HeaderUtil;
import com.pri.cabzza.web.rest.util.PaginationUtil;
import com.pri.cabzza.web.rest.dto.StockWalletDTO;
import com.pri.cabzza.web.rest.mapper.StockWalletMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing StockWallet.
 */
@RestController
@RequestMapping("/api")
public class StockWalletResource {

    private final Logger log = LoggerFactory.getLogger(StockWalletResource.class);

    @Inject
    private StockWalletRepository stockWalletRepository;

    @Inject
    private StockWalletMapper stockWalletMapper;

    @Inject
    private StockWalletSearchRepository stockwalletSearchRepository;

    /**
     * POST  /stockWallets -> Create a new stockwallet.
     */
    @RequestMapping(value = "/stockWallets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockWalletDTO> createStockWallet(@Valid @RequestBody StockWalletDTO stockwalletDTO) throws URISyntaxException {
        log.debug("REST request to save StockWallet : {}", stockwalletDTO);
        if (stockwalletDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new stockwallet cannot already have an ID").body(null);
        }
        StockWallet stockwallet = stockWalletMapper.stockWalletDTOToStockWallet(stockwalletDTO);
        StockWallet result = stockWalletRepository.save(stockwallet);
        stockwalletSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stockWallets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stockwallet", result.getId().toString()))
            .body(stockWalletMapper.stockWalletToStockWalletDTO(result));
    }

    /**
     * PUT  /stockWallets -> Updates an existing stockwallet.
     */
    @RequestMapping(value = "/stockWallets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockWalletDTO> updateStockWallet(@Valid @RequestBody StockWalletDTO stockwalletDTO) throws URISyntaxException {
        log.debug("REST request to update StockWallet : {}", stockwalletDTO);
        if (stockwalletDTO.getId() == null) {
            return createStockWallet(stockwalletDTO);
        }
        StockWallet stockwallet = stockWalletMapper.stockWalletDTOToStockWallet(stockwalletDTO);
        StockWallet result = stockWalletRepository.save(stockwallet);
        stockwalletSearchRepository.save(stockwallet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stockwallet", stockwalletDTO.getId().toString()))
            .body(stockWalletMapper.stockWalletToStockWalletDTO(result));
    }

    /**
     * GET  /stockWallets -> get all the stockWallets.
     */
    @RequestMapping(value = "/stockWallets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StockWalletDTO>> getAllStockWallets(Pageable pageable)
        throws URISyntaxException {
        Page<StockWallet> page = stockWalletRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stockWallets");
        return new ResponseEntity<>(page.getContent().stream()
            .map(stockWalletMapper::stockWalletToStockWalletDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /stockWallets/:id -> get the "id" stockwallet.
     */
    @RequestMapping(value = "/stockWallets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StockWalletDTO> getStockWallet(@PathVariable Long id) {
        log.debug("REST request to get StockWallet : {}", id);
        return Optional.ofNullable(stockWalletRepository.findOne(id))
            .map(stockWalletMapper::stockWalletToStockWalletDTO)
            .map(stockwalletDTO -> new ResponseEntity<>(
                stockwalletDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stockWallets/:id -> delete the "id" stockwallet.
     */
    @RequestMapping(value = "/stockWallets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStockWallet(@PathVariable Long id) {
        log.debug("REST request to delete StockWallet : {}", id);
        stockWalletRepository.delete(id);
        stockwalletSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stockwallet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stockWallets/:query -> search for the stockwallet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stockWallets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StockWalletDTO> searchStockWallets(@PathVariable String query) {
        return StreamSupport
            .stream(stockwalletSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stockWalletMapper::stockWalletToStockWalletDTO)
            .collect(Collectors.toList());
    }
}
