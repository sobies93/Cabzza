package com.pri.cabzza.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.pri.cabzza.domain.StockWallet;
import com.pri.cabzza.repository.StockWalletRepository;
import com.pri.cabzza.repository.search.StockWalletSearchRepository;
import com.pri.cabzza.web.rest.util.HeaderUtil;
import com.pri.cabzza.web.rest.util.PaginationUtil;
import com.pri.cabzza.web.rest.dto.StockWalletDTO;
import com.pri.cabzza.web.rest.mapper.StockWalletMapper;
import org.ojalgo.access.Access2D;
import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.ComplexMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.scalar.ComplexNumber;
import org.ojalgo.scalar.Scalar;
import org.ojalgo.type.context.NumberContext;
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
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
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
public class CalculateResource {

    /**
     * PUT  /calculate.
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



        // Objective function
    /*double[][] P = new double[][] {{ 1., 0.4 }, { 0.4, 1. }};
    PDQuadraticMultivariateRealFunction objectiveFunction = new PDQuadraticMultivariateRealFunction(P, null, 0);

    //equalities
    double[][] A = new double[][]{{1,1}};
    double[] b = new double[]{1};

    //inequalities
    ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[2];
    inequalities[0] = new LinearMultivariateRealFunction(new double[]{-1, 0}, 0);
    inequalities[1] = new LinearMultivariateRealFunction(new double[]{0, -1}, 0);

    //optimization problem
    OptimizationRequest or = new OptimizationRequest();
    or.setF0(objectiveFunction);
    or.setInitialPoint(new double[] { 0.1, 0.9});
    or.setFi(inequalities); //if you want x>0 and y>0
    or.setA(A);
    or.setB(b);
    or.setToleranceFeas(1.E-12);
    or.setTolerance(1.E-12);

    //optimization
    JOptimizer opt = new JOptimizer();
    opt.setOptimizationRequest(or);
    int returnCode = opt.optimize();*/
    }
}
