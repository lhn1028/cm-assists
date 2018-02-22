package cn.org.craftsmen.ms.assist.temp;

import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.org.craftsmen.ms.assist.AssistApplication;

@ActiveProfiles({"dev", "test"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AssistApplication.class})
public class H2DatabaseTest {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Test
	public void testJavaLocale() {
		String sql = "select id,language,country,variant,display_name from java_locale";
		jdbc.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				long id = rs.getLong(1);
				String language = rs.getString(2);
				String country = rs.getString(3);
				String variant = rs.getString(4);
				String displayName = rs.getString(5);
				
				System.out.println(String.format("%d: %s %s %s %s", id, language, country, variant, displayName));
			}});
	}
//	
//	@Test
//	public void testLanguageCodeBaidu() {
//		assertNotNull(jdbc);
//		
//		String sql = "SELECT id,code,display_name FROM language_code_baidu";
//		jdbc.query(sql, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				long id = rs.getLong(1);
//				String code = rs.getString(2);
//				String displayName = rs.getString(3);
//				
//				System.out.println(String.format("%d: %s - %s", id, code, displayName));
//			}
//			
//		});
//	}
//	
//	@Test
//	public void testJavaLocaleBaiduMapping() {
//		String sql = "select java_locale_id,baidu_code_id from java_locale_baidu_mapping";
//		jdbc.query(sql, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				long javaLocaleId = rs.getLong(1);
//				long baiduCodeId = rs.getLong(2);
//				
//				System.out.println(String.format("%d - %d", javaLocaleId, baiduCodeId));
//			}});
//	}
	
//	@Test
//	public void testViewSql() {
//		String sql = "select j.id as locale_id,j.language as language,j.country as country,j.variant as variant,j.display_name as locale_name,b.id as baidu_id,b.code as baidu_code,b.display_name as baidu_name from java_locale as j left join java_locale_baidu_mapping as m on j.id=m.java_locale_id left join language_code_baidu as b on m.baidu_code_id=b.id";
//		
//		jdbc.query(sql, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				long localeId = rs.getLong(1);
//				String language = rs.getString(2);
//				String country = rs.getString(3);
//				String variant = rs.getString(4);
//				String localeDisplayName = rs.getString(5);
//				long baiduId = rs.getLong(6);
//				String baiduCode = rs.getString(7);
//				String baiduDisplayName = rs.getString(8);
//				
//				System.out.println(String.format("%d,%s,%s,%s,%s; %d,%s,%s", localeId,language,country,variant,localeDisplayName,baiduId,baiduCode,baiduDisplayName));
//			}});
//	}
	
//	@Test
//	public void testView() {
//		final String SQL = "select locale_id,language,country,variant,locale_name,baidu_id,baidu_code,baidu_name from v_java_locale_baidu_mapping";
//		jdbc.query(SQL, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				long localeId = rs.getLong(1);
//				String language = rs.getString(2);
//				String country = rs.getString(3);
//				String variant = rs.getString(4);
//				String localeDisplayName = rs.getString(5);
//				long baiduId = rs.getLong(6);
//				String baiduCode = rs.getString(7);
//				String baiduDisplayName = rs.getString(8);
//				
//				System.out.println(String.format("%d,%s,%s,%s,%s; %d,%s,%s", localeId,language,country,variant,localeDisplayName,baiduId,baiduCode,baiduDisplayName));
//			}});
//	}
	
//	@Test
//	public void testViewJavaLocaleBaiduMapping() {
//		String sql = "select locale_id,language,country,variant from v_java_locale_baidu_mapping";
//		jdbc.query(sql, new RowCallbackHandler() {
//
//			@Override
//			public void processRow(ResultSet rs) throws SQLException {
//				long locale_id = rs.getLong(1); 
//				System.out.println(locale_id);
//			}});
//	}
}
