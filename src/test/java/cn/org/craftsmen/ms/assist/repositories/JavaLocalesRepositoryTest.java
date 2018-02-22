package cn.org.craftsmen.ms.assist.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.org.craftsmen.ms.assist.AssistApplication;
import cn.org.craftsmen.ms.assist.entities.JavaLocale;

@ActiveProfiles({"test"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistApplication.class})
public class JavaLocalesRepositoryTest {
	
	@Autowired
	private JavaLocalesRepository repo;

//	@Test
//	public void testSaveS() {
//		assertNotNull(repo);
//		
//		Locale[] avaliableLocales = Locale.getAvailableLocales();
//		for (Locale locale : avaliableLocales) {
//			JavaLocale javaLocale = new JavaLocale(locale.getLanguage(), locale.getCountry(), locale.getVariant(), locale.getDisplayName());
//			
//			repo.save(javaLocale);
//		}
//	}

	@Test
	public void testFindAll() {
		Iterable<JavaLocale> locales = repo.findAll();
		assertThat(locales).isNotEmpty();
		
		for (JavaLocale locale : locales) {
			assertThat(locale).isNotNull();
		}
	}
}
