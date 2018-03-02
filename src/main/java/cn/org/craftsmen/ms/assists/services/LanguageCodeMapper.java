package cn.org.craftsmen.ms.assists.services;

import java.util.List;
import java.util.Locale;

/***
 * Get specific language code by java Locale object
 * @author Shawn
 *
 */
public interface LanguageCodeMapper {
	/***
	 * Get specific language code by java Locale object 
	 * @param locale
	 * @return Specific language code of implemented platform
	 * @throws
	 * IllegalArgumentException When locale is null
	 */
	String getLanguageCode(Locale locale);
	/***
	 * Get supported locales
	 * @return
	 * Supported locales, empty list if even no supported locale
	 */
	List<Locale> getSupportLocales();
}
