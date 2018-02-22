package cn.org.craftsmen.ms.assist.repositories;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.org.craftsmen.ms.assist.AssistApplication;
import cn.org.craftsmen.ms.assist.entities.BaiduCodeMapping;

@ActiveProfiles({"test"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistApplication.class})
public class BaiduCodeMappingRepositoryTest {
	
	@Autowired
	private BaiduCodeMappingRepository repo;

	@Test
	public void testFindByLanguageAndCountry() {
		assertNotNull(repo);
		final Locale EXPECT_LOCALE = Locale.CHINA;
		final String EXPECT_CODE = "zh";
		
		BaiduCodeMapping code = repo.findByLanguageAndCountry(EXPECT_LOCALE.getLanguage(), EXPECT_LOCALE.getCountry());
		assertNotNull(code);
		assertNotNull(code.getBaiduLanguageCode());
		assertEquals(EXPECT_CODE, code.getBaiduLanguageCode());
	}
	
	@Test
	public void testFindByLanguageAndCountryIsNone() {
		assertNotNull(repo);
		final Locale EXPECT_LOCALE = new Locale("zh", "SINGAPORE");
		
		BaiduCodeMapping code = repo.findByLanguageAndCountry(EXPECT_LOCALE.getLanguage(), EXPECT_LOCALE.getCountry());
		assertNull(code);
	}

	@Test
	public void testFindByBaiduLanguageCodeIsNotNull() {
		assertNotNull(repo);
		
		List<BaiduCodeMapping> mappings = repo.findByBaiduLanguageCodeIsNotNull();
		assertNotNull(mappings);
		assertTrue(0 < mappings.size());

		for (BaiduCodeMapping mapping : mappings) {
			assertNotNull(mapping.getBaiduLanguageCode());
		}
	}
}
