package cn.org.craftsmen.ms.assists.sys;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.org.craftsmen.ms.assists.config.EmbeddedDatabaseConfig;

@ActiveProfiles({"test"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {EmbeddedDatabaseConfig.class})
public class DatabaseTest {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testViewData() {
		assertNotNull(jdbcTemplate);
		
		String SQL = "SELECT COUNT(*) FROM v_java_locale_baidu_mapping";
		jdbcTemplate.query(SQL, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				int count = rs.getInt(1);
				assertTrue(0 < count);
			}});
	}
	
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
}
