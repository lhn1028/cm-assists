package cn.org.craftsmen.ms.assists.services;

import java.util.List;
import java.util.Locale;

public interface LanguageCodeMapper {
	String getLanguageCode(Locale locale);
	List<Locale> getSupportLocales();
}
