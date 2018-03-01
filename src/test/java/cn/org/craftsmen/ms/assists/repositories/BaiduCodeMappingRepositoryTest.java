package cn.org.craftsmen.ms.assists.repositories;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import cn.org.craftsmen.ms.assists.AssistsApplication;
import cn.org.craftsmen.ms.assists.config.EmbeddedDatabaseConfig;
import cn.org.craftsmen.ms.assists.entities.BaiduCodeMapping;
import cn.org.craftsmen.ms.assists.repositories.BaiduCodeMappingRepository;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes= {EmbeddedDatabaseConfig.class})
@SpringBootTest(classes= {AssistsApplication.class})
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
