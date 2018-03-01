package cn.org.craftsmen.ms.assists.config;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Profile({"prod", "dev"})
@Configuration
@ConfigurationProperties(prefix="application.data")
public class DatabaseConfig {
	
	@Value("${application.data.bonecp.driverClass}")
	private String jdbcDriverClass;
	@Value("${application.data.bonecp.url}")
	private String jdbcUrl;
	@Value("${application.data.bonecp.username}")
	private String jdbcUsername;
	@Value("${application.data.bonecp.password}")
	private String jdbcPassword;
	@Value("${application.data.bonecp.idleConnectionTestPeriodInMinutes}")
	private int jdbcIdleConnectionTestPeriodInMinutes;
	@Value("${application.data.bonecp.idleMaxAgeInMinutes}")
	private int jdbcIdleMaxAgeInMinutes;
	@Value("${application.data.bonecp.maxConnectionsPerPartition}")
	private int jdbcMaxConnectionsPerPartition;
	@Value("${application.data.bonecp.minConnectionsPerPartition}")
	private int jdbcMinConnectionsPerPartition;
	@Value("${application.data.bonecp.partitionCount}")
	private int jdbcPartitionCount;
	@Value("${application.data.bonecp.acquireIncrement}")
	private int jdbcAcquireIncrement;
	@Value("${application.data.bonecp.statementsCacheSize}")
	private int jdbcStatementsCacheSize;
	
	@Bean(destroyMethod="close")
	public DataSource bonecpDataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(jdbcDriverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		dataSource.setIdleConnectionTestPeriodInMinutes(jdbcIdleConnectionTestPeriodInMinutes);
		dataSource.setIdleMaxAgeInMinutes(jdbcIdleMaxAgeInMinutes);
		dataSource.setMaxConnectionsPerPartition(jdbcMaxConnectionsPerPartition);
		dataSource.setMinConnectionsPerPartition(jdbcMinConnectionsPerPartition);
		dataSource.setPartitionCount(jdbcPartitionCount);
		dataSource.setAcquireIncrement(jdbcAcquireIncrement);
		dataSource.setStatementsCacheSize(jdbcStatementsCacheSize);
		
		return dataSource;
	}
	
	@Value("${application.data.mongo.host}")
	private String mongoHost;
	@Value("${application.data.mongo.port}")
	private int mongoPort;
	@Value("${application.data.mongo.username}")
	private String mongoUsername;
	@Value("${application.data.mongo.password}")
	private String mongoPassword;
	@Value("${application.data.mongo.database}")
	private String mongoDatabase;
	@Value("${application.data.mongo.authenticationDatabase}")
	private String mongoAuthenticationDatabase;
	
	@Bean
	public MongoClient mongoClient() {
		ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
		MongoCredential credential = MongoCredential.createScramSha1Credential(mongoUsername, mongoAuthenticationDatabase, mongoPassword.toCharArray());
		List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
		credentialList.add(credential);
		
		MongoClient mongoClient = new MongoClient(serverAddress, credentialList);
		
		return mongoClient;
	}
	
	@Bean
	public MongoDbFactory mongodbFactory(MongoClient mongoClient) {
		MongoDbFactory mongodbFactory = new SimpleMongoDbFactory(mongoClient, mongoDatabase);
		return mongodbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongodbFactory) {
		return new MongoTemplate(mongodbFactory);
	}
}
