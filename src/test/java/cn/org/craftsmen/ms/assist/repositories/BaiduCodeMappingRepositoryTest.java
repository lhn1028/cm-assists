package cn.org.craftsmen.ms.assist.repositories;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.org.craftsmen.ms.assist.AssistApplication;
import cn.org.craftsmen.ms.assist.config.dev.DatabaseConfig;
import cn.org.craftsmen.ms.assist.config.test.TestDatabaseConfig;
import cn.org.craftsmen.ms.assist.entities.BaiduCodeMapping;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@SpringBootTest
//@Import(TestDatabaseConfig.class)
@SpringBootTest(classes= {AssistApplication.class})
public class BaiduCodeMappingRepositoryTest {
	
	@Autowired
	private BaiduCodeMappingRepository repo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	@Test
//	public void testViewData() {
//		assertNotNull(jdbcTemplate);
//		
//		String SQL = "SELECT COUNT(*) FROM v_java_locale_baidu_mapping";
//		jdbcTemplate.query(SQL, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				int count = rs.getInt(1);
//				assertTrue(0 < count);
//			}});
//	}
	
	@Test
	public void testJavaLocaleData() {
		String sql = "SELECT COUNT(*) FROM java_locale";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				int count = rs.getInt(1);
				assertTrue(0 < count);
			}});
	}
	
	@Test
	public void testLanguageCodeBaiduData() {
		String sql = "SELECT COUNT(*) FROM language_code_baidu";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				int count = rs.getInt(1);
				assertTrue(0 < count);
			}});
	}
	
	@Test
	public void testJavaLocaleBaiduMappingData() {
		String sql = "SELECT COUNT(*) FROM java_locale_baidu_mapping";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				int count = rs.getInt(1);
				assertTrue(0 < count);
			}});
	}

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
