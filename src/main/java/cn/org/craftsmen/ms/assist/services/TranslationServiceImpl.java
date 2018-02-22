package cn.org.craftsmen.ms.assist.services;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import cn.org.craftsmen.ms.assist.api.Translator;

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
		return translator.translate(content, from, to);
	}

}
