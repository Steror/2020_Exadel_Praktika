package practice.guestregistry;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

//import org.springframework.data.mongodb.core.MongoTemplate;

//import practice.guestregistry.guestregistrydata.guestregistrydataimpl;

@SpringBootApplication(exclude = {
//		MongoAutoConfiguration.class,
//	RepositoryRestMvcAutoConfiguration.class
},
scanBasePackages = "practice.guestregistry"
)
//@ComponentScan({"practice.guestregistry"})
public class GuestRegistryApplication implements CommandLineRunner {

//	@Autowired
//	private PersonService personService;
//	@Autowired
//	private CardService cardService;
//	@Autowired
//	private LocationService locationService;
//	@Autowired
//	private EventService eventService;
////	@Autowired
////	private MongoTemplate mongoTemplate;
//	@Autowired
//	private WorkerService workerService;
//	@Autowired
//	private WorkerDTOMapper workerDTOMapper;

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

//		locationService.deleteAllLocations();
//		Location location = new Location();
//		location.setName("a");
//		location.setCountry("b");
//		location.setCity("c");
//		location.setAddress("d");
//		location.setLocationType(LocationType.OFFICE);
//		location.setPhoneNumber("777");
//		Location locationForEvent = locationService.addLocation(location);
//
//		cardService.deleteAll();
//		cardService.addCard(new Card(ObjectId.get(),
//				"serial",
//				location,
//				LocalDateTime.now(),
//				LocalDateTime.now(),
//				CardType.GUEST));
//		cardService.addCard(new Card(ObjectId.get(),
//				"SERIAL",
//				location,
//				LocalDateTime.now(),
//				LocalDateTime.now(),
//				CardType.PERSONNEL));
//		cardService.addCard(new Card(ObjectId.get(),
//				"good number",
//				location,
//				LocalDateTime.now(),
//				LocalDateTime.now(),
//				CardType.PERSONNEL));
//		Card savedCard = cardService.addCard(new Card(ObjectId.get(),
//				"soso serial",
//				location,
//				LocalDateTime.now(),
//				LocalDateTime.now(),
//				CardType.GUEST));

//		personService.deleteAll();

//		PersonDTO person = new PersonDTO();
//		person.setEmail("email@email.com");
//		person.setFirstName("First name");
//		person.setLastName("Last name");
//		person.setPhoneNumber("12345");
//		personService.savePerson(person);


//		personService.savePerson(mapper.map(new Person(ObjectId.get(), "firstPerson", "mname", "lname", "emaill", "phone_nr")));
//		Person person = new Person(ObjectId.get(), "secondPerson", "mname", "lname", "emaill", "phone_nr");
//		Person personForEvent = personService.addPerson(person);
//
//		eventService.deleteAllEvents();
//		Event event = new Event();
//		event.setName("a");
//		event.setDescription("b");
//		event.setParticipants_amount(10);
//		event.setStart_date_time(LocalDateTime.now());
//		event.setEnd_date_time(LocalDateTime.now());
//		event.setLocation(locationForEvent);
//		event.setAttendees(Collections.singletonList(personForEvent));
//		eventService.addEvent(event);

//		workerService.deleteAll();
//		workerService.addWorker(workerDTOMapper.map(new Worker(null, person, savedCard)));
//		workerService.addWorker(workerDTOMapper.map(new Worker(null, person, null)));


//		WorkerDTO workerDTO = new WorkerDTO();
//		workerService.addWorker(workerDTOMapper.map(new Worker(null, person, savedCard)));
//		workerService.addWorker(workerDTOMapper.map(new Worker(null, person, null)));

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
	public ApplicationRunner commandLineRunner () {
		return args -> {
			System.out.println("Hi from Application Runner");
		};
	}

//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.basePackage("practice.guestregistry")).build();
//	}
}
