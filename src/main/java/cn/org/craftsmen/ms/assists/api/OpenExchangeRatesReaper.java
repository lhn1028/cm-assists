package cn.org.craftsmen.ms.assists.api;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.org.craftsmen.ms.assists.domain.ExchangeRates;
import cn.org.craftsmen.ms.assists.exceptions.ReapExchangeRatesException;

@Service
public class OpenExchangeRatesReaper implements ExchangeRatesReaper {
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class OpenExchangeRates {
		private String disclaimer;
		private String license;
		private long timestamp;
		private String base;
		private Map<String, Double> rates;

		public long getTimestamp() {
			return timestamp;
		}

		public String getBase() {
			return base;
		}

		public Map<String, Double> getRates() {
			return rates;
		}

		@Override
		public String toString() {
			return "ExchangeRate [disclaimer=" + disclaimer + ", license=" + license + ", timestamp=" + timestamp
					+ ", base=" + base + ", rates=" + rates.size() + "]";
		}
	}
	
	private static final String NAME = "openexchangerates.org";
	private static final String URL = "https://openexchangerates.org/api/latest.json";
	private static final String APP_ID = "c0a991e82d4b47e4b1a536a8de6b4dbe";
	private static final String BASE = "USD";

	@Autowired
	private RestTemplate rest;

	@Override
	public ExchangeRates reap() {
		
		try {
			OpenExchangeRates openRates = rest.getForObject(URL + "?app_id={appId}&base={base}", OpenExchangeRates.class, APP_ID, BASE);
			ExchangeRates rates = new ExchangeRates();
			rates.setSource(NAME);
			rates.setUpdateDate(new Date(openRates.getTimestamp() * 1000L));
			rates.setBase(openRates.getBase());
			rates.setRates(openRates.getRates());
			
			return rates;
		} catch (RestClientException ex) {
			throw new ReapExchangeRatesException(String.format("Reap exchange rates on %s?app_id=%s&base=%s", URL, APP_ID, BASE), ex);
		}
	}

}
