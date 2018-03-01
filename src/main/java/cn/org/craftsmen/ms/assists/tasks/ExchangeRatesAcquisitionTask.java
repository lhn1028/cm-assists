package cn.org.craftsmen.ms.assists.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.org.craftsmen.ms.assists.api.ExchangeRatesReaper;
import cn.org.craftsmen.ms.assists.entities.ExchangeRates;
import cn.org.craftsmen.ms.assists.exceptions.ReapExchangeRatesException;
import cn.org.craftsmen.ms.assists.repositories.ExchangeRatesRepository;

@Component
public class ExchangeRatesAcquisitionTask {
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRatesAcquisitionTask.class);
	
	private ExchangeRatesReaper exchangeRatesReaper;
	private ExchangeRatesRepository exchangeRatesRepository;
	

	@Autowired
	public ExchangeRatesAcquisitionTask(
			@Qualifier("openExchangeRatesReaper") 
			ExchangeRatesReaper exchangeRatesReaper,
			ExchangeRatesRepository exchangeRatesRepository
			) {
		this.exchangeRatesReaper = exchangeRatesReaper;
		this.exchangeRatesRepository = exchangeRatesRepository;
	}
	
	@Scheduled(cron = "0 0 9,16 * * ?")
	public void reapExchangeRates() {
		try {
			ExchangeRates exchangeRates = exchangeRatesReaper.reap();
			exchangeRatesRepository.saveExchangeRates(exchangeRates);
			
			log.info(String.format("Reap and save exchange rates: %s", exchangeRates.toString()));
		} catch (ReapExchangeRatesException e) {
			log.error(e.getMessage());
		}
	}
}
