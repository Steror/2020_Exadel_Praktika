//package practice.guestregistry.services_tests;
//
//import org.bson.types.ObjectId;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import practice.guestregistry.exceptions.InvalidDocumentStateException;
//import practice.guestregistry.exceptions.ResourceNotFoundException;
//import practice.guestregistry.models.Card;
//import practice.guestregistry.models.CardType;
//import practice.guestregistry.models.Location;
//import practice.guestregistry.models.Worker;
//import practice.guestregistry.services.CardService;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static practice.guestregistry.models.LocationType.OFFICE;
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@AutoConfigureDataMongo
//@ActiveProfiles("test")
//public class CardServiceTests {
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//    @Autowired
//    CardService cardService;
//
//    /*dates aren't checked */
//
//    @Before
//    public void cleanUp() {
//        for (String name : mongoTemplate.getCollectionNames()) {
//            mongoTemplate.dropCollection(name);
//        }
//    }
//
//    @Test
//    public void add_card_when_location_exist_in_db() {
//        Location location = new Location();
//        mongoTemplate.save(location);
//
//        Card card = new Card();
//        card.setSerialNumber("a");
//        card.setCtype(CardType.PERSONNEL);
//        card.setManufactured(LocalDateTime.now());
//        card.setValidUntil(LocalDateTime.now());
//        Card savedCard = cardService.addCard(card);
//
//        long collectionSize = mongoTemplate.findAll(Card.class).size();
//        assertThat(savedCard.getId()).isNotNull();
//        assertThat(collectionSize).isEqualTo(1);
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void add_card_when_location_dont_exist_in_Db() {
//        Location location = new Location();
//        Card card = new Card();
//        card.setSerialNumber("a");
//        card.setCtype(CardType.PERSONNEL);
//        card.setManufactured(LocalDateTime.now());
//        card.setValidUntil(LocalDateTime.now());
//        Card savedCard = cardService.addCard(card);
//    }
//
//
//
//    @Test
//    public void get_all_cards() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        mongoTemplate.save(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        assertThat(mongoTemplate.findAll(Card.class).size()).isEqualTo(3);
//        assertThat(cardService.getAllCards().size()).isEqualTo(3);
//    }
//
//    @Test
//    public void get_existing_card_by_id() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        mongoTemplate.save(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        Card saveCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
//        assertThat(cardService.getCardById(saveCard.getId()).get()).isEqualTo(saveCard);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void get_not_existing_card_by_id() {
//
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        mongoTemplate.save(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        Card saveCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        Card newCard = new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL);
//        cardService.getCardById(newCard.getId());
//    }
//
//
//
//    @Test
//    public void delete_existing_worker_by_id() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        mongoTemplate.save(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        cardService.deleteCardById(savedCard.getId());
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(2);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void delete_not_existing_worker_by_id() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        mongoTemplate.save(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        mongoTemplate.save(new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        Card newCard = new Card(ObjectId.get(), "s2", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL);
//        cardService.deleteCardById(newCard.getId());
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void dont_update_not_existing_id() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        savedCard.setId(ObjectId.get());
//        cardService.updateCard(savedCard);
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void update_when_location_is_null() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        cardService.addCard(new Card(savedCard.getId(), "s3", null, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void update_when_location_is_not_in_db() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//
//        Location location2 = new Location();
//        location2.setId(ObjectId.get());
//        location2.setName("B");
//        location2.setCountry("Lietuva");
//        location2.setCity("Vilnius");
//        location2.setAddress("Zalgirio 90");
//        location2.setLocationType(OFFICE);
//        location2.setPhoneNumber("851212345");
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        cardService.updateCard(new Card(savedCard.getId(), "s3", location2, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//    }
//
//    @Test
//    public void update_when_person_is_in_db_correct_update() {
//        //adding card
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//        Location savedLocation = mongoTemplate.save(location1);
//        Card savedCard = cardService.addCard(new Card(ObjectId.get(), "s1", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//
//        //creating new person to update, and add him to db
//        Location updatingWithLocation = new Location();
//        updatingWithLocation.setId(ObjectId.get());
//        updatingWithLocation.setName("B");
//        updatingWithLocation.setCountry("Lietuva");
//        updatingWithLocation.setCity("Vilnius");
//        updatingWithLocation.setAddress("Zalgirio 90");
//        updatingWithLocation.setLocationType(OFFICE);
//        updatingWithLocation.setPhoneNumber("851212345");
//        updatingWithLocation.setId(ObjectId.get());
//        mongoTemplate.save(updatingWithLocation);
//
//        // updating to new Worker
//        Card newCard = new Card(savedCard.getId(), "s2", updatingWithLocation, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL);
//        cardService.updateCard(newCard);
//
//        System.out.println(mongoTemplate.findAll(Card.class));
//        assertThat(savedCard).isEqualTo(mongoTemplate.findAll(Card.class).get(0));
//    }
//
//    @Test
//    public void card_exist() {
//        Location location1 = new Location();
//        location1.setId(ObjectId.get());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType(OFFICE);
//        location1.setPhoneNumber("851212345");
//        Card savedCard = mongoTemplate.save(new Card(ObjectId.get(), "s3", location1, LocalDateTime.now(), LocalDateTime.now(), CardType.PERSONNEL));
//        assertThat(cardService.cardExist(savedCard)).isTrue();
//    }
//
//}
