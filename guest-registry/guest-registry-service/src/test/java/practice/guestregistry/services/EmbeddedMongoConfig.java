package practice.guestregistry.services;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

//TODO:it should use core application-test.properties instead
//@ComponentScan(basePackages = "practice.guestregistry")
//@PropertySource("file:${project.basedir}/guest-registry-core/src/main/resources/core:application-test.properties")
//@PropertySource(value = "application-test.properties")
//@PropertySource("file:guest-registry-core/src/main/resources/application-test.properties")
//@PropertySource({"file:guest-registry-core/src/main/resources/application-${envTarget:dev}.properties"})
//@AutoConfigureCache
@PropertySource({"classpath:application.properties"})
public class EmbeddedMongoConfig {
    private static final Logger log = LoggerFactory.getLogger(EmbeddedMongoConfig.class);
    private static MongodProcess mongoProcess;

    @Value("${spring.data.mongodb.host}")
    private String MONGO_HOST;
    @Value("${spring.data.mongodb.database}")
    private String MONGO_DATABASE;
    @Value("${spring.data.mongodb.port}")
    private String MONGO_PORT;
    private final String testCollection = "testCol";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new SimpleMongoClientDbFactory(
                "mongodb://"
                        + MONGO_HOST + ":"
                        + MONGO_PORT + "/"
                        + MONGO_DATABASE));
    }

    @PostConstruct
    public void start () throws IOException {
        log.trace("read from param file:");
        log.trace("MONGO_DATABASE:" + MONGO_DATABASE);
        log.trace("MONGO_HOST:" + MONGO_HOST);
        log.trace("MONGO_PORT:"+MONGO_PORT);
        log.trace("" + Integer.decode(MONGO_PORT));

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongoExecutable = starter.prepare(
                new MongodConfigBuilder()
                        .version(Version.Main.PRODUCTION)
                        .net(new Net(MONGO_HOST, Integer.decode(MONGO_PORT), false))
                        .build());
        try {
            mongoProcess = mongoExecutable.start();
            MongoClient mongo = MongoClients.create("mongodb://"
                    + MONGO_HOST + ":"
                    + MONGO_PORT + "/"
                    + MONGO_DATABASE);
//            DB db = mongo.getDB("test");
            MongoDatabase mongoDatabase = mongo.getDatabase("test");
            mongoDatabase.createCollection(testCollection);
            MongoCollection testCol = mongoDatabase.getCollection(testCollection);
            testCol.insertOne(new BasicDBObject("testDoc", new Date()));
        } catch (Exception ex) {
            if (mongoProcess != null) {
                mongoProcess.stop();
            }
            mongoProcess = mongoExecutable.start();
        }
    }

    @PreDestroy
    public static void stop() {
        if (mongoProcess != null) {
            mongoProcess.stop();
        }
    }

}
