package cn.org.craftsmen.ms.assists.services;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.craftsmen.ms.assists.api.Translator;

@Service
public class TranslationServiceImpl implements TranslationService {

	private Translator translator;

	@Autowired
	public TranslationServiceImpl(
			@Qualifier("baiduTranslator") 
			Translator translator) {
		this.translator = translator;
	}

	@Override
	public String translate(String content, Locale from, Locale to) {
		if (null == content || "".equals(content.trim())) {
			throw new IllegalArgumentException("Content of translation must not be null or empty");
		}
		if (null == from || null == to) {
			throw new IllegalArgumentException("The locale of `from` and `to` must not be null");
		}
		return translator.translate(content, from, to);
	}

}
