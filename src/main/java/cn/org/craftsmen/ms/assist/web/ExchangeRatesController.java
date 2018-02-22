package cn.org.craftsmen.ms.assist.web;

import java.util.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.org.craftsmen.ms.assist.exceptions.BadRequestException;
import cn.org.craftsmen.ms.assist.services.ExchangeRatesService;
import cn.org.craftsmen.ms.assist.web.response.ExchangeRateConversionResponse;

@RestController
@RequestMapping("/er")
public class ExchangeRatesController {
	
	@Autowired
	private ExchangeRatesService exchangeRatesService;
	
	@RequestMapping("/convert")
	public ExchangeRateConversionResponse convert(double amount, String from, String to) {
		
		if (0 > amount) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "invalid_amount", "Invalid currency amount - please try again, or contact support@craftsmen.org.cn");
		}
		
		try {
			Currency currencyFrom = Currency.getInstance(from);
			Currency currencyTo = Currency.getInstance(to);
			
			double result = exchangeRatesService.convert(amount, currencyFrom, currencyTo);
			
			ExchangeRateConversionResponse exchangeRateConversionResult = new ExchangeRateConversionResponse();
			ExchangeRateConversionResponse.Request exchangeRateConversionRequest = new ExchangeRateConversionResponse.Request();
			exchangeRateConversionRequest.setAmount(amount);
			exchangeRateConversionRequest.setFrom(from);
			exchangeRateConversionRequest.setTo(to);
			exchangeRateConversionResult.setReqest(exchangeRateConversionRequest);
			exchangeRateConversionResult.setResult(result);
			
			return exchangeRateConversionResult;
		} catch (NullPointerException e) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "invalid_currency", "Invalid currency symbol - please try again, or contact support@craftsmen.org.cn");
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "invalid_currency", "Invalid currency symbol - please try again, or contact support@craftsmen.org.cn");
		} 
		
	}
}
