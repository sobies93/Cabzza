package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.pri.cabzza.web.rest.util.HeaderUtil;

import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



/**
 * REST controller for managing Calculation.
 */
@RestController
@RequestMapping("/api")
public class CalculateResource {

    /**
     * PUT  /calculate/:id - calculating NewStockWallet mathematical values and  related PortfolioStores
     */
    @RequestMapping(value = "/calculate",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void calculate() throws Exception {

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
