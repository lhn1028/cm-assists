package cn.org.craftsmen.ms.assist.repositories;

import cn.org.craftsmen.ms.assist.domain.ExchangeRates;

/***
 * Customize method of exchange rates repository
 * 
 * @author shawn
 *
 */
public interface ExchangeRatesRepositoryCustom {
	/***
	 * Find newest exchange rates data from database.
	 * @return The newest exchange rates or null if exchange rates data not founded
	 */
	ExchangeRates findLastExchangeRates();
	/***
	 * Save exchange rates into database
	 * @param exchangeRates
	 */
	void saveExchangeRates(ExchangeRates exchangeRates);
}
