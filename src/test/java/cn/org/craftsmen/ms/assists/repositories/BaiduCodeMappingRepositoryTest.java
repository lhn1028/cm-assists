package cn.org.craftsmen.ms.assists.repositories;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import cn.org.craftsmen.ms.assists.config.EmbeddedDatabaseConfig;
import cn.org.craftsmen.ms.assists.entities.BaiduCodeMapping;
import cn.org.craftsmen.ms.assists.repositories.BaiduCodeMappingRepository;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {EmbeddedDatabaseConfig.class})
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
	public void testFindByLanguageAndCountryWithNull() {
		BaiduCodeMapping code = repo.findByLanguageAndCountry(null, "CN");
		assertNull(code);
		
		code = repo.findByLanguageAndCountry("zh", null);
		assertNull(code);
		
		code = repo.findByLanguageAndCountry(null, null);
		assertNull(code);
	}
	
	@Test
	public void testFindByLanguageAndCountryWithEmpty() {
		BaiduCodeMapping code = repo.findByLanguageAndCountry("", "CN");
		assertNull(code);
		
		code = repo.findByLanguageAndCountry("zh", "");
		assertNotNull(code);
		
		code = repo.findByLanguageAndCountry("", "");
		assertNull(code);
		
		code = repo.findByLanguageAndCountry("  ", "CN");
		assertNull(code);
		
		code = repo.findByLanguageAndCountry("zh", "  ");
		assertNotNull(code);
		
		code = repo.findByLanguageAndCountry("  ", "  ");
		assertNull(code);
	}
	
	@Test
	public void testFindByLanguageAndCountryWithUnavailableLocale() {
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
