package cn.org.craftsmen.ms.assists.services;

import java.util.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.craftsmen.ms.assists.api.ExchangeRatesReaper;
import cn.org.craftsmen.ms.assists.domain.ExchangeRates;
import cn.org.craftsmen.ms.assists.exceptions.ExchangeRateConversionException;
import cn.org.craftsmen.ms.assists.repositories.ExchangeRatesRepository;

@Service
class ExchangeRatesServiceImpl implements ExchangeRatesService {
	
	private ExchangeRatesRepository exchangeRatesRepository;
	private ExchangeRatesReaper exchangeRatesReaper;
	
	@Autowired
	public ExchangeRatesServiceImpl(ExchangeRatesRepository exchangeRatesRepository, ExchangeRatesReaper exchangeRatesReaper) {
		this.exchangeRatesRepository = exchangeRatesRepository;
		this.exchangeRatesReaper = exchangeRatesReaper;
	}

	@Override
	public double convert(double amount, Currency from, Currency to) {
		if (0 > amount || null == from || null == to) {
			throw new IllegalArgumentException(String.format("Argument amount: %d, from: %s, to: %s error", amount, from, to));
		}
		
		try {
			ExchangeRates rates = exchangeRatesRepository.findLastExchangeRates();
			Double fromRate = rates.getRates().get(from.getCurrencyCode());
			Double toRate = rates.getRates().get(to.getCurrencyCode());
			
			if (null == fromRate || null == toRate) {
				throw new ExchangeRateConversionException(500, String.format("Not support exchange form %s to %s", from.getCurrencyCode(), to.getCurrencyCode()), null);
			}
			
			if (new Double(0).equals(fromRate)) {
				throw new ExchangeRateConversionException(500, "Currency of source(from) can not be zero", null);
			}
			
			return new Double(0).equals(amount) ? 0 : (toRate / fromRate) * amount;
		} catch (ClassCastException e) {
			throw new ExchangeRateConversionException(500, "Exchange rate conversion error", e);
		} catch (NullPointerException e) {
			throw new ExchangeRateConversionException(500, "Exchange rate conversion error", e);
		}
		
	}

	@Override
	public void update() {
		ExchangeRates exchangeRates = exchangeRatesReaper.reap();
		exchangeRatesRepository.saveExchangeRates(exchangeRates);
	}

}
