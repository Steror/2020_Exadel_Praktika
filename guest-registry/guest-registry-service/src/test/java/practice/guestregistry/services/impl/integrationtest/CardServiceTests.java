package practice.guestregistry.services.impl.integrationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.mongo.daoimpl.CardDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.data.mongo.mappers.LocationMapper;
import practice.guestregistry.data.mongo.testconfig.EmbeddedMongoConfig;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.CardService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.serviceimpl.CardServiceImpl;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CardServiceImpl.class, LocationServiceImpl.class,
                            CardDaoImpl.class, LocationDaoImpl.class,
                            CardMapper.class, LocationMapper.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
//@AutoConfigureDataMongo
//@ActiveProfiles("test")
public class CardServiceTests {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CardService cardService;
    @Autowired
    LocationService locationService;

    /*dates aren't checked */

    @Before
    public void cleanUp() {
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
    }

    @Test
    public void add_card_when_location_exist_in_db() {
        Location location = new Location();
//        location = locationService.addLocation(location);

        Card card = new Card();
        card.setSerialNumber("a");
        card.setLocation(location);
        card.setCtype("PERSONNEL");
        card.setManufactured(LocalDateTime.now());
        card.setValidUntil(LocalDateTime.now());
//        Card savedCard = cardService.addCard(card);

        long collectionSize = cardService.getAllCards().size();
//        assertThat(savedCard.getId()).isNotNull();
        assertThat(collectionSize).isEqualTo(1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void add_card_when_location_dont_exist_in_Db() {
        Location location = new Location();
        Card card = new Card();
        card.setSerialNumber("a");
        card.setCtype("PERSONNEL");
        card.setManufactured(LocalDateTime.now());
        card.setValidUntil(LocalDateTime.now());
//        Card savedCard = cardService.addCard(card);
    }

    @Test
    public void get_all_cards() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

        cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
        cardService.addCard(new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
        cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));

        assertThat(cardService.getAllCards().size()).isEqualTo(3);
    }

    @Test
    public void get_existing_card_by_id() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

        cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
        cardService.addCard(new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        Card saveCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));

        assertThat(cardService.getAllCards().size()).isEqualTo(3);
//        assertThat(cardService.getCardById(saveCard.getId())).isEqualTo(saveCard);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void get_not_existing_card_by_id() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

        cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
        cardService.addCard(new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        Card saveCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));

        Card newCard = new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL");
        cardService.getCardById(newCard.getId());
    }



//    @Test//TODO worker ir card vienoje vietoje? Trinti arba perrasyti
//    public void delete_existing_worker_by_id() {
//        Location location1 = new Location();
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType("OFFICE");
//        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);
//
//        cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        cardService.addCard(new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//
//        cardService.deleteCardById(savedCard.getId());
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(2);
//    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_not_existing_worker_by_id() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

        cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
        cardService.addCard(new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));

        Card newCard = new Card(null, "s2", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL");
        cardService.deleteCardById(newCard.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dont_update_not_existing_id() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));

//        savedCard.setId(null);
//        cardService.updateCard(savedCard);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_when_location_is_null() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        cardService.addCard(new Card(savedCard.getId(), "s3", null, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_when_location_is_not_in_db() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

        Location location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Vilnius");
        location2.setAddress("Zalgirio 90");
        location2.setLocationType("OFFICE");
        location2.setPhoneNumber("851212345");

//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        cardService.updateCard(new Card(savedCard.getId(), "s3", location2, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
    }

//    @Test //TODO Card nesusijes su person, keistas testas: trinti arba perrasyti
//    public void update_when_person_is_in_db_correct_update() {
//        //adding card
//        Location location1 = new Location();
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType("OFFICE");
//        location1.setPhoneNumber("851212345");
//        Location savedLocation = locationService.addLocation(location1);
//
//        Card savedCard = cardService.addCard(new Card(null, "s1", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//
//        //creating new person to update, and add him to db
//        Location updatingWithLocation = new Location();
//        updatingWithLocation.setName("B");
//        updatingWithLocation.setCountry("Lietuva");
//        updatingWithLocation.setCity("Vilnius");
//        updatingWithLocation.setAddress("Zalgirio 90");
//        updatingWithLocation.setLocationType("OFFICE");
//        updatingWithLocation.setPhoneNumber("851212345");
//        updatingWithLocation = locationService.addLocation(updatingWithLocation);
//
//        // updating to new Worker
//        Card newCard = new Card(savedCard.getId(), "s2", updatingWithLocation, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL");
//        cardService.updateCard(newCard);
//
//        assertThat(savedCard).isEqualTo(cardService.getAllCards().get(0));
//    }

    @Test
    public void card_exist() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");
//        location1 = locationService.addLocation(location1);

//        Card savedCard = cardService.addCard(new Card(null, "s3", location1, LocalDateTime.now(), LocalDateTime.now(), "PERSONNEL"));
//        assertThat(cardService.cardExist(savedCard)).isTrue();
    }
}
