package practice.guestregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;

@SpringBootApplication(scanBasePackages = "practice.guestregistry", exclude = {
        EmbeddedMongoAutoConfiguration.class
})
public class GuestRegistryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryServiceApp.class, args);
    }
}
