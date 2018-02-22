package cn.org.craftsmen.ms.assist.services;

import java.util.Locale;

/***
 * 
 * @author shawn
 *
 */
public interface TranslationService {
	/***
	 * 
	 * @param content
	 * @param from
	 * @param to
	 * @return
	 * @throws TranslateException 
	 */
	String translate(String content, Locale from, Locale to);

}
