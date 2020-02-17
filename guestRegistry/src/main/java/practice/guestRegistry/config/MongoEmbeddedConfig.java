package practice.guestRegistry.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

@Configuration
public class MongoEmbeddedConfig {
    private Environment environment;

    @Autowired
    public MongoEmbeddedConfig (Environment environment) {
        this.environment = environment;
    }

    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient mongoClient () {
        int port = this.environment.getProperty("local.mongo.port", Integer.class);
        return new MongoClient("localhost", port);
    }
}
