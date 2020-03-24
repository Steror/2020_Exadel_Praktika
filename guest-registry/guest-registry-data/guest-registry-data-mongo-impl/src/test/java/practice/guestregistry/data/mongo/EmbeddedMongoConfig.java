package practice.guestregistry.data.mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

//TODO:it should use core application-test.properties instead
//@ComponentScan(basePackages = "practice.guestregistry")
@Configuration
//@PropertySource("file:${project.basedir}/guest-registry-core/src/main/resources/core:application-test.properties")
//@PropertySource(value = "application-test.properties")
//@PropertySource("file:guest-registry-core/src/main/resources/application-test.properties")
//@PropertySource({"file:guest-registry-core/src/main/resources/application-${envTarget:dev}.properties"})
@PropertySource({"application.properties"})
public class EmbeddedMongoConfig {
    private MongodProcess mongoProcess;
    @Value("${spring.data.mongodb.host}")
    private String MONGO_HOST;
    @Value("${spring.data.mongodb.database}")
    private String MONGO_DATABASE;
    @Value("${spring.data.mongodb.port}")
    private String MONGO_PORT;

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

        System.out.println("\n\n\n-----------------------------------------------------------------------");
        System.out.println(MONGO_DATABASE);
        System.out.println(MONGO_HOST);
        System.out.println(MONGO_PORT);
        System.out.println(Integer.decode(MONGO_PORT));
        System.out.println("\n\n\n-----------------------------------------------------------------------");

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongoExecutable = starter.prepare(
                new MongodConfigBuilder()
                        .version(Version.Main.PRODUCTION)
                        .net(new Net(MONGO_HOST, Integer.decode(MONGO_PORT), false))
                        .build());
        mongoProcess = mongoExecutable.start();
    }

    @PreDestroy
    public void stop() {
        mongoProcess.stop();
    }

}
