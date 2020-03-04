package practice.guestregistry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import practice.guestregistry.models.*;
import practice.guestregistry.services.*;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Collections;

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
	private EventService eventService;
//	@Autowired
//	private MongoTemplate mongoTemplate;
	@Autowired
	private WorkerService workerService;

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

//	@Profile("test")
	@Override
	public void run(String... args) throws Exception {

		locationService.deleteAllLocations();
		Location location = new Location();
		location.setName("a");
		location.setCountry("b");
		location.setCity("c");
		location.setAddress("d");
		location.setLocationType(LocationType.OFFICE);
		location.setPhoneNumber("777");
		location = locationService.addLocation(location);

		cardService.deleteAll();
		cardService.addCard(new Card(ObjectId.get(),
				"serial",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.GUEST));
		cardService.addCard(new Card(ObjectId.get(),
				"SERIAL",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.PERSONNEL));
		cardService.addCard(new Card(ObjectId.get(),
				"good number",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.PERSONNEL));
		cardService.addCard(new Card(ObjectId.get(),
				"soso serial",
				location,
				LocalDateTime.now(),
				LocalDateTime.now(),
				CardType.GUEST));

		personService.deleteAll();
		personService.addPerson(new Person(ObjectId.get(), "firstName", "mname", "lname", "emaill", "phone_nr"));
		Person person = new Person(ObjectId.get(), "firstName", "mname", "lname", "emaill", "phone_nr");
		Person personForEvent = personService.addPerson(person);

		eventService.deleteAllEvents();
		Event event = new Event();
		event.setName("a");
		event.setDescription("b");
		event.setParticipants_amount(10);
		event.setStart_date_time(LocalDateTime.now());
		event.setEnd_date_time(LocalDateTime.now());
		event.setLocation(location);
		event.setAttendees(Collections.singletonList(personForEvent));
		eventService.addEvent(event);

		workerService.deleteAll();
		workerService.addWorker(new Worker(ObjectId.get(), person, null));
		workerService.addWorker(new Worker(ObjectId.get(), person, null));
		System.out.println("from app.run()");
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
