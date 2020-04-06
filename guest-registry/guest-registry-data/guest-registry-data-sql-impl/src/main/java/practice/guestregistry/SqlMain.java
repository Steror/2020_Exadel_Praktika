//package practice.guestregisry;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import practice.guestregistry.data.api.dao.LocationDao;
//
//@SpringBootApplication(scanBasePackages = "practice.guestregistry")
////@SpringBootApplication
//public class SqlMain {
//    LocationDao dao;
//
//    @Autowired
//    SqlMain(@Qualifier("h2") LocationDao dao) {
//        this.dao = dao;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(SqlMain.class);
//        app.run(args);
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner1 () {
//        return args -> {
//
//
//            System.out.println("Hi from CommandLine Runner");
//        };
//    }
//
//    @Bean
//    public ApplicationRunner commandLineRunner () {
//        return args -> {
//            System.out.println("Hi from Application Runner");
//        };
//    }
//}
