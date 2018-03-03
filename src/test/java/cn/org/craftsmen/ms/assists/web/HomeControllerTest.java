package cn.org.craftsmen.ms.assists.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cn.org.craftsmen.ms.assists.config.RestClientConfig;
import cn.org.craftsmen.ms.assists.config.WebMvcConfig;

//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers= {HomeController.class})
// @ContextConfiguration(classes= {RestClientConfig.class, WebMvcConfig.class})
// @SpringBootTest
public class HomeControllerTest {

//	@Autowired
//    private WebApplicationContext webApplicationContext;
	
	// @Autowired
	private MockMvc mvc;

	@Before
	public void setUp() {
//		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
	}

	@Test
	public void testHome() throws Exception {
//		assertThat(webApplicationContext).isNotNull();
		assertThat(mvc).isNotNull();
		mvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(content().string("Assist services online"))
		.andDo(print());
	}

}
