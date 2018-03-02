package cn.org.craftsmen.ms.assists.repositories;

import cn.org.craftsmen.ms.assists.entities.ExchangeRates;

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
	 * @throws
	 * IllegalArgumentException If exchangeRates is null or empty
	 */
	void saveExchangeRates(ExchangeRates exchangeRates);
}
