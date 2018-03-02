package cn.org.craftsmen.ms.assists.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Locale;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import cn.org.craftsmen.ms.assists.api.Translator;

public class TranslationServiceImplTest {

	private Translator translator = mock(Translator.class);
	private TranslationService service;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		service = new TranslationServiceImpl(translator);
	}

	@Test
	public void testTranslate() {
		final Locale SOURCE_LOCALE = Locale.CHINA;
		final Locale TARGET_LOCALE = Locale.ENGLISH;
		final String SOURCE_CONTENT = "翻译内容";
		final String EXPECTED_TRANSLATION = "translation content";

		when(translator.translate(SOURCE_CONTENT, SOURCE_LOCALE, TARGET_LOCALE)).thenReturn(EXPECTED_TRANSLATION);

		assertThat(service.translate(SOURCE_CONTENT, SOURCE_LOCALE, TARGET_LOCALE))
				.isEqualToIgnoringCase(EXPECTED_TRANSLATION);
	}

	@Test
	public void testTranslateWithNullContent() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Content of translation must not be null or empty");
		service.translate(null, Locale.CHINA, Locale.ENGLISH);
	}

	@Test
	public void testTranslateWithEmptyContent() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Content of translation must not be null or empty");
		service.translate("  ", Locale.CHINA, Locale.ENGLISH);
	}
	
	@Test
	public void testTranslateWithNullLocale() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The locale of `from` and `to` must not be null");
		service.translate("Some content", null, null);
	}
}
