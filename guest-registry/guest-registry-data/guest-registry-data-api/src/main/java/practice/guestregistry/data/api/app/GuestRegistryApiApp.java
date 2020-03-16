package practice.guestregistry.data.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "practice.guestregistry.data.api")
public class GuestRegistryApiApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryApiApp.class, args);
    }
}
