package cn.org.craftsmen.ms.assists.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.craftsmen.ms.assists.exceptions.TranslateException;
import cn.org.craftsmen.ms.assists.services.LanguageCodeMapper;


public class BaiduTranslatorTest {
	private List<Locale> supportedLocales;
	private LanguageCodeMapper codeMapper = mock(LanguageCodeMapper.class);
	private BaiduTranslator translator;

	private static final class JsonLocale {
		private String language;
		private String country;
		private String variant;

		public String getLanguage() {
			return language;
		}

		public String getCountry() {
			return country;
		}

		public String getVariant() {
			return variant;
		}

		@Override
		public String toString() {
			return "JsonLocale [language=" + language + ", country=" + country + ", variant=" + variant + "]";
		}
	}

	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, JsonLocale.class);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("data/support_locale_list.json");
		List<JsonLocale> allLocales = objectMapper.readValue(in, type);
		supportedLocales = allLocales.stream().map(
				jsonLocale -> new Locale(jsonLocale.getLanguage(), jsonLocale.getCountry(), jsonLocale.getVariant()))
				.collect(Collectors.toList());
		in.close();

		translator = new BaiduTranslator(codeMapper, new ObjectMapper(), new RestTemplate());
	}

	@Test
	public void testGetSupportLocales() {
		when(codeMapper.getSupportLocales()).thenReturn(supportedLocales);
		Locale[] locales = translator.getSupportLocales();
		assertThat(locales).isNotEmpty();
		assertEquals(120, locales.length);
		
		when(codeMapper.getSupportLocales()).thenReturn(new ArrayList<Locale>());
		locales = translator.getSupportLocales();
		assertThat(locales).isEmpty();
		assertEquals(0, locales.length);
	}

	@Test
	public void testBuildSign() throws UnsupportedEncodingException {
		final String CONTENT = "翻译内容";
		final long SALT = 1516086000L;
		final String EXPECTED_SIGN = "356fc2e61d5aa3cc1a262fdd412a5032";

		String sign = BaiduTranslator.buildSign(CONTENT, SALT).toLowerCase();

		assertEquals(EXPECTED_SIGN, sign);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testTranslate() {
		final Locale EXPECTED_SOURCE_LOCALE = Locale.CHINA;
		final Locale EXPECTED_TARGET_LOCALE = Locale.ENGLISH;

		when(codeMapper.getLanguageCode(EXPECTED_SOURCE_LOCALE)).thenReturn("zh");
		when(codeMapper.getLanguageCode(EXPECTED_TARGET_LOCALE)).thenReturn("en");
		final String CONTENT = "翻译内容";

		try {
			final String EXPECTED_RESULT = "translation content";
			String result = translator.translate(CONTENT, EXPECTED_SOURCE_LOCALE, EXPECTED_TARGET_LOCALE);
			assertEquals(EXPECTED_RESULT, result.toLowerCase());
		} catch (TranslateException e) {
			final int EXPECTED_CODE = 511;
			final String EXPECTED_CAUSE_CODE = "58000";
			assertEquals(EXPECTED_CODE, e.getErrorCode());
			assertTrue(e.getMessage().startsWith(EXPECTED_CAUSE_CODE));
		}
	}
	
	@Test
	public void testTranslateWithNullContent() {
		final Locale EXPECTED_SOURCE_LOCALE = Locale.CHINA;
		final Locale EXPECTED_TARGET_LOCALE = Locale.ENGLISH;

		when(codeMapper.getLanguageCode(EXPECTED_SOURCE_LOCALE)).thenReturn("zh");
		when(codeMapper.getLanguageCode(EXPECTED_TARGET_LOCALE)).thenReturn("en");
		final String CONTENT = null;

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Content must not be empty");
		String result = translator.translate(CONTENT, EXPECTED_SOURCE_LOCALE, EXPECTED_TARGET_LOCALE);
		assertThat(result).isEmpty();
	}

	@Test
	public void testTranslateWithEmptyContent() {
		final Locale EXPECTED_SOURCE_LOCALE = Locale.CHINA;
		final Locale EXPECTED_TARGET_LOCALE = Locale.ENGLISH;

		when(codeMapper.getLanguageCode(EXPECTED_SOURCE_LOCALE)).thenReturn("zh");
		when(codeMapper.getLanguageCode(EXPECTED_TARGET_LOCALE)).thenReturn("en");
		final String CONTENT = " ";

		thrown.expect(IllegalArgumentException.class);
		thrown.expect(Matchers.hasProperty("message", is("Content must not be empty")));
		String result = translator.translate(CONTENT, EXPECTED_SOURCE_LOCALE, EXPECTED_TARGET_LOCALE);
		assertThat(result).isEmpty();
	}
	
	@Test
	public void testTranslateWithNullLocale() {
		final Locale EXPECTED_SOURCE_LOCALE = null;
		final Locale EXPECTED_TARGET_LOCALE = null;

		when(codeMapper.getLanguageCode(EXPECTED_SOURCE_LOCALE)).thenReturn("zh");
		when(codeMapper.getLanguageCode(EXPECTED_TARGET_LOCALE)).thenReturn("en");
		final String CONTENT = "翻译内容";
		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The locale of `from` and `to` must not be null");

		String result = translator.translate(CONTENT, EXPECTED_SOURCE_LOCALE, EXPECTED_TARGET_LOCALE);
		assertThat(result).isEmpty();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTranslateWithAllParamNull() {
		final Locale EXPECTED_SOURCE_LOCALE = null;
		final Locale EXPECTED_TARGET_LOCALE = null;

		when(codeMapper.getLanguageCode(EXPECTED_SOURCE_LOCALE)).thenReturn("zh");
		when(codeMapper.getLanguageCode(EXPECTED_TARGET_LOCALE)).thenReturn("en");
		final String CONTENT = null;

		String result = translator.translate(CONTENT, EXPECTED_SOURCE_LOCALE, EXPECTED_TARGET_LOCALE);
		assertThat(result).isEmpty();
	}
}
