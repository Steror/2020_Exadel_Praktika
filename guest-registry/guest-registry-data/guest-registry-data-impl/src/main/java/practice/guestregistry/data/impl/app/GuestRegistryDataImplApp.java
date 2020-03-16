package practice.guestregistry.data.impl.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "practice.guestregistry.data.impl")
public class GuestRegistryDataImplApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryDataImplApp.class, args);
    }
}
