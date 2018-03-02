package cn.org.craftsmen.ms.assists.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import cn.org.craftsmen.ms.assists.config.RestClientConfig;
import cn.org.craftsmen.ms.assists.services.TranslationService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TranslateController.class})
@ContextConfiguration(classes= {RestClientConfig.class})
public class TranslateControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TranslationService translationService;
	
	private static final String REQUEST_CONTENT = "{\r\n" + 
			"\"content\": \"翻译内容\",\r\n" + 
			"\"from\": {\r\n" + 
			"\"language\":\"zh\",\r\n" + 
			"\"country\":\"CN\"\r\n" + 
			"},\r\n" + 
			"\"to\": {\r\n" + 
			"\"language\":\"en\",\r\n" + 
			"\"country\":\"US\"\r\n" + 
			"}\r\n" + 
			"}";

	@Test
	public void testTranslate() throws Exception {
		final Locale SOURCE_LOCALE = Locale.CHINA;
		final Locale TARGET_LOCALE = Locale.ENGLISH;
		final String SOURCE_CONTENT = "翻译内容";
		final String EXPECTED_TRANSLATION = "translation content";
		
		BDDMockito.given(translationService.translate(SOURCE_CONTENT, SOURCE_LOCALE, TARGET_LOCALE)).willReturn(EXPECTED_TRANSLATION);
		mvc.perform(post("/trans/")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(REQUEST_CONTENT))
		.andExpect(status().isOk())
		.andExpect(content().string(EXPECTED_TRANSLATION));
	}

}
