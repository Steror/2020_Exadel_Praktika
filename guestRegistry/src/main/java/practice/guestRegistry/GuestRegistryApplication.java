package practice.guestRegistry;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.CardType;
import practice.guestRegistry.models.Person;
import practice.guestRegistry.services.CardService;
import practice.guestRegistry.services.PersonService;

import java.io.PrintStream;
import java.time.Instant;
import java.util.Date;

@SpringBootApplication(exclude = {
//		MongoAutoConfiguration.class,
})
public class GuestRegistryApplication implements CommandLineRunner {

	@Autowired
	private PersonService personService;
	@Autowired
	private CardService cardService;

	public static void main(String[] args) {
//		SpringApplication.run(GuestRegistryApplication.class, args);
		SpringApplication app = new SpringApplication(GuestRegistryApplication.class);
		app.setBanner(new Banner() {
//		SpringApplicationBuilder app = new SpringApplicationBuilder(GuestRegistryApplication.class);
//		app.child("EXTRA CONFIGURATION"); << this shouldn't work
//		app.banner(new Banner() {
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
		cardService.deleteAll();
		cardService.addCard(new Card(new Long(11),
				"serial",
				Date.from(Instant.now()),
				Date.from(Instant.now()),
				CardType.GUEST));
		cardService.addCard(new Card(new Long(12),
				"SERIAL",
				Date.from(Instant.now()),
				Date.from(Instant.now()),
				CardType.GUEST));


		personService.deleteAll();
		personService.addPerson(new Person((long) 1, "firstName", "mname", "lname", "emaill", "phone_nr"));
		personService.addPerson(new Person((long) 2, "firstName", "mname", "lname", "emaill", "phone_nr"));

	}
}
