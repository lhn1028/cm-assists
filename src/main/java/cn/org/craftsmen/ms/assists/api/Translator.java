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
	 * TranslateException 
	 */
	String translate(String content, Locale from, Locale to);
}
