package cn.org.craftsmen.ms.assist.services;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.org.craftsmen.ms.assist.entities.BaiduCodeMapping;
import cn.org.craftsmen.ms.assist.repositories.BaiduCodeMappingRepository;

@Component
public class BaiduLanguageCodeMapper implements LanguageCodeMapper {
	
	private BaiduCodeMappingRepository baiduCodeMappingRepository;

	@Autowired
	public BaiduLanguageCodeMapper(BaiduCodeMappingRepository baiduCodeMappingRepository) {
		this.baiduCodeMappingRepository = baiduCodeMappingRepository;
	}

	@Override
	public String getLanguageCode(Locale locale) {
		BaiduCodeMapping mapping = baiduCodeMappingRepository.findByLanguageAndCountry(locale.getLanguage(), locale.getCountry());
		return null == mapping ? null : mapping.getBaiduLanguageCode();
	}
	
	@Override
	public List<Locale> getSupportLocales() {
		return baiduCodeMappingRepository
				.findByBaiduLanguageCodeIsNotNull()
				.stream().map(mapping -> new Locale(mapping.getLanguage(), mapping.getCountry()))
				.collect(Collectors.toList());
	}

}
