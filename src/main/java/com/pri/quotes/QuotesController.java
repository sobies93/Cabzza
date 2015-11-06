package com.pri.quotes;

import com.pri.dataproviders.exceptions.DataProviderException;
import com.pri.dataproviders.yahoo.YahooDataProvider;
import com.pri.dataproviders.yahoo.model.YahooHistoricalDataQuote;
import com.pri.home.HomeController;
import java.security.Principal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mikolaj Manski
 */
@Controller
public class QuotesController {

	@Autowired
	private YahooDataProvider dataProvider;

	@RequestMapping(value = "/quotes", method = RequestMethod.POST)
	public String quotesForm(Model model, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("quoteSymbol") String quoteSymbol) {
		List<YahooHistoricalDataQuote> stockQuotes = Collections.EMPTY_LIST;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			stockQuotes = dataProvider.getStockQuotes(quoteSymbol, StringUtils.EMPTY, sdf.parse(startDate), sdf.parse(endDate));
		} catch (ParseException | DataProviderException ex) {
			Logger.getLogger(QuotesController.class.getName()).log(Level.SEVERE, null, ex);
		}
		model.addAttribute("quotes", stockQuotes);
		return "quotes/quotes";
	}

	@RequestMapping(value = "/quotes", method = RequestMethod.GET)
	public String quotes(Model model) {

		return "quotes/quotesForm";
	}
}
