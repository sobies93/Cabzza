package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.pri.cabzza.domain.NewStockWallet;
import com.pri.cabzza.domain.PortfolioStore;
import com.pri.cabzza.repository.NewStockWalletRepository;
import com.pri.cabzza.repository.PortfolioStoreRepository;
import com.pri.cabzza.web.rest.util.HeaderUtil;

import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Calculation.
 */
@RestController
@RequestMapping("/api")
public class CalculateResource {

    @Inject
    private NewStockWalletRepository newStockWalletRepository;

    @Inject
    private PortfolioStoreRepository portfolioStoreRepository;

    /**
     * PUT  /calculate/:id - calculating NewStockWallet mathematical values and  related PortfolioStores
     */
    @RequestMapping(value = "/calculate/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void calculate(@PathVariable Long id ) throws Exception {

        NewStockWallet currentWallet = newStockWalletRepository.findOne(id);
        List <PortfolioStore> currentPortfolio = portfolioStoreRepository.findAllByNewStockWallet(currentWallet);
        for( PortfolioStore stock : currentPortfolio){

        }

        //just testing MarkovitzModel, will be change in the future
        double[][] CArray = {{1.,0.,0.},{0.,0.5,0.},{0.,0.,1.}};
        double[][] RArray = {{1,0,-1}};
        BasicMatrix <Double> covariance = PrimitiveMatrix.FACTORY.rows(CArray);
        BasicMatrix <Double> returns = PrimitiveMatrix.FACTORY.rows(RArray);
        MarkowitzModel currentModel = new MarkowitzModel(covariance,returns);
        currentModel.setShortingAllowed(false);
        currentModel.setTargetReturn(BigDecimal.ZERO);
        System.out.print(currentModel.getWeights());

    }
}
