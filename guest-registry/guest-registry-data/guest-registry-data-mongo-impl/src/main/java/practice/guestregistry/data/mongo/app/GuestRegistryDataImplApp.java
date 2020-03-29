package practice.guestregistry.data.mongo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;

@SpringBootApplication(scanBasePackages = "practice.guestregistry.data")
public class GuestRegistryDataImplApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryDataImplApp.class, args);
    }
}
