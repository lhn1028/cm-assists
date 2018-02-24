package cn.org.craftsmen.ms.assist.services;

import java.util.Currency;

public interface ExchangeRatesService {
	/***
	 * Use specified currency convert amount from source currency to destination currency
	 * @param amount Amount will be converted
	 * @param from Source currency
	 * @param to Target currency
	 * @return Target amount of (to) currency
	 * @throws IllegalArgumentException If abount less than 0 or or from or to currency is null
	 * @throws ExchangeRateConversionException When convert error, or from/to currency is not supported
	 */
	double convert(double amount, Currency from, Currency to);

	/***
	 * Manual update exchange rates
	 */
	void update();
}
