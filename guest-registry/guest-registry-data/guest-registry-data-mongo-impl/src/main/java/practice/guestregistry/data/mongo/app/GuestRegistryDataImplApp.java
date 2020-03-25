package practice.guestregistry.data.mongo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "practice.guestregistry.data.mongo")
public class GuestRegistryDataImplApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryDataImplApp.class, args);
    }
}
