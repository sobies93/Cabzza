package com.pri.cabzza.service;

import com.pri.cabzza.domain.NewStockWallet;
import com.pri.cabzza.domain.PortfolioStore;
import com.pri.cabzza.domain.StockQuotes;
import com.pri.cabzza.service.util.StockQuotesComperator;
import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by ja on 2016-01-07.
 */
@Service
public class CalculateService {

    public NewStockWallet calculate (NewStockWallet startingWallet){
        List<PortfolioStore> portfolioContent= new ArrayList<>(startingWallet.getPortfolioStores());
        List <List<Double>> returns = new ArrayList<>();
        for(int i = 0 ; i < portfolioContent.size();i++) {
            PortfolioStore content = portfolioContent.get(i);
            returns.add(new ArrayList<Double>() {});
            returns.get(i).addAll(returnsCalculation(filterByDate(new ArrayList<>(content.getStockInfo().getStockQuotess()),
                startingWallet.getHistoricalDataDate(), startingWallet.getCalculatingsDate())));
        }
        ArrayList <Double> expextedReturns = new ArrayList<>();
        ArrayList <ArrayList <Double>> correlation = new ArrayList<>();
        for(int k = 0 ; k < portfolioContent.size();k++) {
            expextedReturns.add(calculateReturn(returns.get(k)));
        }
        ArrayList <ArrayList <Double>> corelation = new ArrayList<>();
        for(int k = 0 ; k < portfolioContent.size();k++) {
            ArrayList <Double> temp = new ArrayList<>();
            for(int j = 0 ; j < portfolioContent.size();j++) {
                temp.add(calculateCorelation(returns.get(k), returns.get(j), expextedReturns.get(k), expextedReturns.get(j)));
            }
            corelation.add(temp);
        }
        Double CovariationArray[][] = new Double [corelation.size()][];

        for (int i = 0; i < corelation.size(); i++) {
            ArrayList<Double> row = corelation.get(i);
            CovariationArray[i] = row.toArray(new Double[row.size()]);
        }
        BasicMatrix <Double> BMcovariance = PrimitiveMatrix.FACTORY.rows(CovariationArray);
        BasicMatrix <Double> BMreturns = PrimitiveMatrix.FACTORY.rows(expextedReturns);
        MarkowitzModel currentModel = new MarkowitzModel(BMcovariance,BMreturns);
        currentModel.setShortingAllowed(false);
        if(startingWallet.getExpectedReturn()!=null)
            currentModel.setTargetReturn(new BigDecimal(startingWallet.getExpectedReturn()));
        else if(startingWallet.getExpectedVariation()!= null)
            currentModel.setTargetVariance(new BigDecimal(startingWallet.getExpectedVariation()));
        List<BigDecimal> weights= currentModel.getWeights();
        Double expextedReturn = new Double(0);
        Double expectedVariation = new Double(0);
        for(int m =0; m<portfolioContent.size();m++){
            PortfolioStore currentStock = portfolioContent.get(m);
            expextedReturn += weights.get(m).doubleValue()*expextedReturns.get(m);
            currentStock.setPercent(weights.get(m).doubleValue()*100);
        }
        for(int m =0; m<portfolioContent.size();m++) {
            for (int k = 0; k < portfolioContent.size(); k++) {
                expectedVariation+=CovariationArray[m][k]*weights.get(m).doubleValue()*weights.get(k).doubleValue();
            }
        }
        expectedVariation = Math.sqrt(expectedVariation);
        startingWallet.setExpectedVariation(expectedVariation);
        startingWallet.setExpectedReturn(expextedReturn);
        startingWallet.setSharpRatio(expextedReturn/expectedVariation*100);
        return startingWallet;
    }

    private static Double calculateCorelation (List <Double> returnsA, List <Double> returnsB, Double expectedA, Double expectedB){
        Double sum = new Double(0);
        for(int i =0; i< returnsA.size() ; i++) {
            sum+=(returnsA.get(i)-expectedA)*(returnsB.get(i)-expectedB);
        }
        return sum/returnsA.size();
    }

    private static Double calculateReturn (List <Double> returns){
        Double sumReturns = new Double(0);
        for(Double singleReturn:returns){
            sumReturns +=singleReturn;
        }
        return sumReturns/returns.size();
    }

    private static ArrayList <Double> returnsCalculation (List<StockQuotes> quotes ){
        ArrayList <Double> returns = new ArrayList<>();
        for(int i = 1;i < quotes.size(); i++) {
            Double currentSplitRate = (quotes.get(i).getSplitRate()!= null ? quotes.get(i).getSplitRate() : Double.valueOf(1));
            Double currentDividend = (quotes.get(i).getDividend()!= null ? quotes.get(i).getDividend() : Double.valueOf(0));
            returns.add((quotes.get(i).getValue()*currentSplitRate-quotes.get(i-1).getValue()+currentDividend)
                /quotes.get(i-1).getValue()*100);
        }
        return returns;
    }

    private static ArrayList <StockQuotes> filterByDate (List <StockQuotes> allQuotes, LocalDate startDate, LocalDate endDate) {
        Collections.sort(allQuotes, new StockQuotesComperator());
        ArrayList <StockQuotes> filter = new ArrayList<>();
        for(StockQuotes quote : allQuotes){
            if(quote.getDate().isAfter(startDate.minusDays(1))&&quote.getDate().isBefore(endDate.plusDays(1))){
                filter.add(quote);
            }
        }
        return filter;
    }
}
