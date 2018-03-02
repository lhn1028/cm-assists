package cn.org.craftsmen.ms.assists.api;

import java.util.Locale;

/***
 * Language translator interface
 * @author shawn
 *
 */
public interface Translator {
	/***
	 * Language supported by the implementation of Translator
	 * @return
	 */
	Locale[] getSupportLocales();
	/***
	 * 
	 * @param content
	 * @param from
	 * @param to
	 * @return
	 * @throws
	 * IllegalArgumentException 
	 * When content is null or empty
	 * When Locale `from` or `to` is null
	 * TranslateException 
	 */
	String translate(String content, Locale from, Locale to);
}
