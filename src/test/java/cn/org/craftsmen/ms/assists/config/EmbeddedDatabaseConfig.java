package cn.org.craftsmen.ms.assists.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile("test")
@Configuration
//@EnableJpaRepositories(basePackages= {"cn.org.craftsmen.ms.assists.repositories"})
public class EmbeddedDatabaseConfig {
	@Bean
	public DataSource embeddedDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName("assists")
				.addScript("classpath:/sql/schema.sql").addScript("classpath:/sql/data.sql").build();
	}
	
//	@Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPackagesToScan("cn.org.craftsmen.ms.assists.entities");
//        entityManagerFactoryBean.setJpaProperties(buildHibernateProperties());
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {{
//            setDatabase(Database.HSQL);
//        }});
//        return entityManagerFactoryBean;
//    }
//
//    protected Properties buildHibernateProperties()
//    {
//        Properties hibernateProperties = new Properties();
//
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        hibernateProperties.setProperty("hibernate.show_sql", "true");
//        hibernateProperties.setProperty("hibernate.use_sql_comments", "false");
//        hibernateProperties.setProperty("hibernate.format_sql", "true");
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
//        hibernateProperties.setProperty("hibernate.generate_statistics", "false");
//        hibernateProperties.setProperty("javax.persistence.validation.mode", "none");
//
//        //Audit History flags
//        hibernateProperties.setProperty("org.hibernate.envers.store_data_at_delete", "true");
//        hibernateProperties.setProperty("org.hibernate.envers.global_with_modified_flag", "true");
//
//        return hibernateProperties;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager();
//    }
//
//    @Bean
//    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
//        return new TransactionTemplate(transactionManager);
//    }
}
