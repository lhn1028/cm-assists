package cn.org.craftsmen.ms.assists.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.craftsmen.ms.assists.domain.Language;
import cn.org.craftsmen.ms.assists.services.TranslationService;
import cn.org.craftsmen.ms.assists.web.request.TranslateRequest;
import cn.org.craftsmen.ms.assists.web.response.TranslateResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TranslateController.class})
@AutoConfigureWebClient
public class TranslateControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TranslationService translationService;
	
	private final Locale SOURCE_LOCALE = Locale.CHINA;
	private final Locale TARGET_LOCALE = Locale.ENGLISH;
	private final String SOURCE_CONTENT = "翻译内容";
	private final String EXPECTED_TRANSLATION = "translation content";
	
	private String requestContent;
	
	@Before
	public void setUp() throws JsonProcessingException {
		TranslateRequest req = new TranslateRequest();
		req.setContent(SOURCE_CONTENT);
		req.setFrom(new Language(SOURCE_LOCALE.getLanguage(), SOURCE_LOCALE.getCountry()));
		req.setTo(new Language(TARGET_LOCALE.getLanguage(), TARGET_LOCALE.getCountry()));
		
		ObjectMapper mapper = new ObjectMapper();
		requestContent = mapper.writeValueAsString(req);
	}
	

	@Test
	public void testTranslate() throws Exception {		
		BDDMockito.given(translationService.translate(SOURCE_CONTENT, SOURCE_LOCALE, TARGET_LOCALE)).willReturn(EXPECTED_TRANSLATION);
		MvcResult res = mvc.perform(post("/trans")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestContent)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print()).andReturn();
		
		String body = res.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		TranslateResponse tr = mapper.readValue(body, TranslateResponse.class);
		assertEquals(EXPECTED_TRANSLATION, tr.getTranslateResult());
	}

}
