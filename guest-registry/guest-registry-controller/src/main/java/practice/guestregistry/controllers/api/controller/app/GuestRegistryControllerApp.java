package practice.guestregistry.controllers.api.controller.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "practice.guestregistry.api")
public class GuestRegistryControllerApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestRegistryControllerApp.class, args);
    }
}
