package cn.org.craftsmen.ms.assists.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.craftsmen.ms.assists.entities.BaiduCodeMapping;
import cn.org.craftsmen.ms.assists.repositories.BaiduCodeMappingRepository;

public class BaiduLanguageCodeMapperTest {

	private List<BaiduCodeMapping> supportedLocales;
	private BaiduCodeMappingRepository repo = mock(BaiduCodeMappingRepository.class);
	private LanguageCodeMapper codeMapper;
	
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		when(repo.findByLanguageAndCountry("zh", "")).thenReturn(new BaiduCodeMapping(146, "zh", "", "zh"));
		when(repo.findByLanguageAndCountry("zh", "CN")).thenReturn(new BaiduCodeMapping(146, "zh", "CN", "zh"));
		when(repo.findByLanguageAndCountry("zh", "TW")).thenReturn(new BaiduCodeMapping(146, "zh", "TW", "cht"));
		when(repo.findByLanguageAndCountry("en", "")).thenReturn(new BaiduCodeMapping(146, "en", "", "en"));
		when(repo.findByLanguageAndCountry("en", "US")).thenReturn(new BaiduCodeMapping(146, "en", "US", "en"));
		when(repo.findByLanguageAndCountry("en", "GB")).thenReturn(new BaiduCodeMapping(146, "en", "GB", "en"));
		when(repo.findByLanguageAndCountry("fr", "")).thenReturn(new BaiduCodeMapping(146, "fr", "", "fra"));
		when(repo.findByLanguageAndCountry("fr", "FR")).thenReturn(new BaiduCodeMapping(146, "fr", "FR", "fra"));
		when(repo.findByLanguageAndCountry("fr", "BE")).thenReturn(new BaiduCodeMapping(146, "fr", "BE", "fra"));

		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, BaiduCodeMapping.class);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("data/baidu_code_mapping_list.json");
		supportedLocales = objectMapper.readValue(in, type);
		in.close();

		codeMapper = new BaiduLanguageCodeMapper(repo);
	}

	@Test
	public void testGetLanguageCode() {
		String languageCode = codeMapper.getLanguageCode(new Locale("zh"));
		assertEquals("zh", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("zh", "CN"));
		assertEquals("zh", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("zh", "TW"));
		assertEquals("cht", languageCode);
		
		languageCode = codeMapper.getLanguageCode(new Locale("en"));
		assertEquals("en", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("en", "US"));
		assertEquals("en", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("en", "GB"));
		assertEquals("en", languageCode);
		
		languageCode = codeMapper.getLanguageCode(new Locale("fr"));
		assertEquals("fra", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("fr", "FR"));
		assertEquals("fra", languageCode);
		languageCode = codeMapper.getLanguageCode(new Locale("fr", "BE"));
		assertEquals("fra", languageCode);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLanguageCodeWithNull() {
		String languageCode = codeMapper.getLanguageCode(null);
		assertNull(languageCode);
	}
	
	@Test
	public void testLanguageCodeWithNone() {
		String languageCode = codeMapper.getLanguageCode(new Locale("axzf"));
		assertNull(languageCode);
	}

	@Test
	public void testGetSupportLocales() {
		when(repo.findByBaiduLanguageCodeIsNotNull()).thenReturn(supportedLocales);
		List<Locale> locales = codeMapper.getSupportLocales();

		assertEquals(120, locales.size());
		for (Locale locale : locales) {
			assertNotNull(locale);
		}
	}

	@Test
	public void testGetSupportLocalesWithRepoReturnNull() {
		when(repo.findByBaiduLanguageCodeIsNotNull()).thenReturn(null);
		List<Locale> locales = codeMapper.getSupportLocales();
		
		assertThat(locales).isEmpty();
	}
}
