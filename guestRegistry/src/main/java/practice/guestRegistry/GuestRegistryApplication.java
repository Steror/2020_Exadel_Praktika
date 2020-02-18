package practice.guestRegistry;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import practice.guestRegistry.dao.LocationDao;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.CardType;
import practice.guestRegistry.models.Location;
import practice.guestRegistry.models.Person;
import practice.guestRegistry.services.CardService;
import practice.guestRegistry.services.LocationService;
import practice.guestRegistry.services.PersonService;

import javax.annotation.PostConstruct;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static practice.guestRegistry.models.LocationType.OFFICE;

@SpringBootApplication(exclude = {
//		MongoAutoConfiguration.class,
})
public class GuestRegistryApplication implements CommandLineRunner {

	@Autowired
	private PersonService personService;
	@Autowired
	private CardService cardService;
	@Autowired
	private LocationService locationService;
	@Autowired
	MongoTemplate mongoTemplate;


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GuestRegistryApplication.class);
		app.setBanner(new Banner() {
//		app.child("EXTRA CONFIGURATION"); << this shouldn't work

			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				System.out.println("         ,--,                                                                                                            ");
				System.out.println("       ,--.'|            ,--,    ,--,                      .--.--.                                                       ");
				System.out.println("    ,--,  | :          ,--.'|  ,--.'|                     /  /    '. ,-.----.             ,--,                           ");
				System.out.println(" ,---.'|  : '          |  | :  |  | :     ,---.          |  :  /`. / \\    /  \\   __  ,-.,--.'|         ,---,             ");
				System.out.println(" |   | : _' |          :  : '  :  : '    '   ,'\\         ;  |  |--`  |   :    |,' ,'/ /||  |,      ,-+-. /  |  ,----._,. ");
				System.out.println(" :   : |.'  |   ,---.  |  ' |  |  ' |   /   /   |        |  :  ;_    |   | .\\ :'  | |' |`--'_     ,--.'|'   | /   /  ' / ");
				System.out.println(" |   ' '  ; :  /     \\ '  | |  '  | |  .   ; ,. :         \\  \\    `. .   : |: ||  |   ,',' ,'|   |   |  ,'' ||   :     | ");
				System.out.println(" '   |  .'. | /    /  ||  | :  |  | :  '   | |: :          `----.   \\|   |  \\ :'  :  /  '  | |   |   | /  | ||   | .\\  . ");
				System.out.println(" |   | :  | '.    ' / |'  : |__'  : |__'   | .; :          __ \\  \\  ||   : .  ||  | '   |  | :   |   | |  | |.   ; ';  | ");
				System.out.println(" '   : |  : ;'   ;   /||  | '.'|  | '.'|   :    |         /  /`--'  /:     |`-';  : |   '  : |__ |   | |  |/ '   .   . | ");
				System.out.println(" |   | '  ,/ '   |  / |;  :    ;  :    ;\\   \\  /         '--'.     / :   : :   |  , ;   |  | '.'||   | |--'   `---`-'| | ");
				System.out.println(" ;   : ;--'  |   :    ||  ,   /|  ,   /  `----'            `--'---'  |   | :    ---'    ;  :    ;|   |/       .'__/\\_: | ");
				System.out.println(" |   ,/       \\   \\  /  ---`-'  ---`-'                               `---'.|            |  ,   / '---'        |   :    : ");
				System.out.println(" '---'         `----'                                                  `---`             ---`-'                \\   \\  /  ");
				System.out.println("                                                                                                                `--`-' ");
 			}
		});
		app.run(args);
	}


	@Override
	public void run(String... args) throws Exception {

		Location location = new Location();
		location.setName("a");
		location.setCountry("b");
		location.setCity("c");
		location.setAddress("d");
		location.setLocationType(OFFICE);
		location.setPhoneNumber("777");
		locationService.addLocation(location);

		cardService.deleteAll();
		cardService.addCard(new Card(11L,
				"serial",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.GUEST));
		cardService.addCard(new Card(12L,
				"SERIAL",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.GUEST));



		personService.deleteAll();
		personService.addPerson(new Person((long) 11, "firstName", "mname", "lname", "emaill", "phone_nr"));
		personService.addPerson(new Person((long) 22, "firstName", "mname", "lname", "emaill", "phone_nr"));


//		db clean up
//		for (String name : mongoTemplate.getCollectionNames()) {
//			System.out.println(name);
//			mongoTemplate.dropCollection(name);
//		}

	}

	@Bean
	public CommandLineRunner commandLineRunner1 () {
		return args -> {

			System.out.println("Hi from CommandLine Runner");
		};
	}

	@Bean
	@Autowired
	public ApplicationRunner commandLineRunner (MongoTemplate mongoTemplate) {
		return args -> {
			System.out.println("Hi from Application Runner");
		};
	}

	//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//			System.out.println("______________________________");
//		};
//	}
}
