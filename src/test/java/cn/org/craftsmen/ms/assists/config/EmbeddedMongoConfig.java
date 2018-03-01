package cn.org.craftsmen.ms.assists.config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Feature;
import de.flapdoodle.embed.mongo.distribution.Versions;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.GenericVersion;
import de.flapdoodle.embed.process.runtime.Network;

@Profile("test")
@Configuration
public class EmbeddedMongoConfig {
	private static final String DOWNLOAD_URL = "http://lib.craftsmen.org.cn/mongo/";
	private static final String MONGO_DB_NAME = "assists";

	@Bean
	public IRuntimeConfig runtimeConfig() {
		Command command = Command.MongoD;

		@SuppressWarnings("deprecation")
		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder().defaults(command)
				.artifactStore(new ExtractedArtifactStoreBuilder().defaults(command)
						.download(new DownloadConfigBuilder().defaultsForCommand(command).downloadPath(DOWNLOAD_URL)))
//				.artifactStore(new ExtractedArtifactStoreBuilder()
//						.defaults(command)
//						.download(new DownloadConfigBuilder()
//								.defaultsForCommand(command)
//								.proxyFactory(new HttpProxyFactory("201.200.200.209", 8580)))
//						)
				.build();

		return runtimeConfig;
	}
	
	@Bean
	public MongodStarter mongoStarter(IRuntimeConfig runtimeConfig) {
		return MongodStarter.getInstance(runtimeConfig);
	}

	@Bean
	public IMongodConfig mongodConfig() throws UnknownHostException, IOException {
		IMongodConfig mongodConfig = new MongodConfigBuilder()
				.version(Versions.withFeatures(new GenericVersion("3.4.0"), Feature.SYNC_DELAY))
				.net(new Net(InetAddress.getLoopbackAddress().getHostAddress(), Network.getFreeServerPort(),
						Network.localhostIsIPv6()))
				.build();

		return mongodConfig;
	}

	@Bean(destroyMethod = "stop")
	public MongodExecutable mongodExecutable(MongodStarter starter, IMongodConfig mongodConfig) {
		return starter.prepare(mongodConfig);
	}

	@Bean(destroyMethod = "stop", name = "embeddedMongoServer")
	public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
		return mongodExecutable.start();
	}

	@Bean
	public MongoTemplate mongoTemplate(MongodProcess mongodProcess) throws IOException, InterruptedException {
		IMongodConfig mongodConfig = mongodProcess.getConfig();
		MongoClient mongoClient = new MongoClient(mongodConfig.net().getBindIp(), mongodConfig.net().getPort());
		return new MongoTemplate(mongoClient, MONGO_DB_NAME);
	}
}
