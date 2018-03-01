package cn.org.craftsmen.ms.assists.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.craftsmen.ms.assists.AssistsApplication;
import cn.org.craftsmen.ms.assists.services.BaiduLanguageCodeMapper;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistsApplication.class})
public class BaiduLanguageCodeMapperTest {
	
	@Autowired
	private BaiduLanguageCodeMapper mapper;

	@Test
	public void testGetLanguageCode() {
		final Locale EXPECT_LOCALE1 = Locale.CHINA;
		final Locale EXPECT_LOCALE2 = Locale.US;
		
		final String EXPECT_CODE1 = "zh";
		final String EXPECT_CODE2 = "en";
		
		assertNotNull(mapper);
		
		String code1 = mapper.getLanguageCode(EXPECT_LOCALE1);
		String code2 = mapper.getLanguageCode(EXPECT_LOCALE2);
		assertEquals(EXPECT_CODE1, code1);
		assertEquals(EXPECT_CODE2, code2);
	}

	@Test
	public void testGetSupportLocales() {
		assertNotNull(mapper);
		List<Locale> supportLocales = mapper.getSupportLocales();
		assertTrue(0 < supportLocales.size());
		
		for (Locale locale : supportLocales) {
			assertNotNull(locale);
		}
	}
}
