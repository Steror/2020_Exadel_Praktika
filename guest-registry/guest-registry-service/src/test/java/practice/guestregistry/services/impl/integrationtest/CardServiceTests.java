package practice.guestregistry.services.impl.integrationtest;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.mongo.daoimpl.CardDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.data.mongo.mappers.LocationMapper;
import practice.guestregistry.services.EmbeddedMongoConfig;
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
import static org.assertj.core.api.Assertions.not;

@SpringBootTest(classes = {CardServiceImpl.class, LocationServiceImpl.class,
                            CardDaoImpl.class, LocationDaoImpl.class,
                            CardMapper.class, LocationMapper.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
@ActiveProfiles("test")
public class CardServiceTests {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CardService cardService;
    @Autowired
    LocationService locationService;
    Location location1;

    final String LOCATION1_NAME = "A";
    final String LOCATION1_COUNTRY = "Lietuva";
    final String LOCATION1_CITY = "Vilnius";
    final String LOCATION1_ADDRESS = "ZALGIRIO 90";
    final String LOCATION1_LOCATION_TYPE = "OFFICE";
    final String LOCATION1_PHONE_NUMBER = "851212345";

    Card card;
    final String CARD_SERIAL = "123456";
    final String CARD_LOCATION = "123456";
    final String CARD_TYPE = "PERSONNEL";
    final String CARD_MANUFACTURED = LocalDateTime.now().toString();
    final String CARD_VALID_UNTIL = "2022-03-25T22:57:00.795";

    @Before
    public void cleanUp() {
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
        location1 = new Location();
        location1.setName(LOCATION1_NAME);
        location1.setCountry(LOCATION1_COUNTRY);
        location1.setCity(LOCATION1_CITY);
        location1.setAddress(LOCATION1_ADDRESS);
        location1.setLocationType(LOCATION1_LOCATION_TYPE);
        location1.setPhoneNumber(LOCATION1_PHONE_NUMBER);
        locationService.addLocation(location1);
//        mongoTemplate.save(location1, "location");


        card = new Card();
        card.setSerialNumber(CARD_SERIAL);
        card.setLocationId(location1.getId());
        card.setLocationName(LOCATION1_NAME);
        card.setCtype(CARD_TYPE);
        card.setManufactured(CARD_MANUFACTURED);
        card.setValidUntil(CARD_VALID_UNTIL);
    }

    @Test
    public void getCardById() {
        cardService.addCard(card);
        assertThat(cardService.getCardById(card.getId())).isEqualTo(card);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCardById_whenCardDoesntExist() {
//        cardService.getCardById(card.getId());
        cardService.getCardById(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCardById_whenCardById_notExist() {
        cardService.getCardById(new ObjectId().toHexString());
    }

    @Test
    public void getAllCards() {
        cardService.addCard(card);
        Card anotherCard = new Card();

        String locationName = "anotherLocation";
        Location anotherLocation = new Location();
        anotherLocation.setName(locationName);
        locationService.addLocation(anotherLocation);

        anotherCard.setLocationId(anotherLocation.getId());
        anotherCard.setLocationName(locationName);
        anotherCard.setManufactured(LocalDateTime.now().toString());
        anotherCard.setValidUntil(CARD_VALID_UNTIL);
        cardService.addCard(anotherCard);

        assertThat(cardService.getAllCards().stream().map(elem -> elem.getLocationName())).
                contains(locationName, LOCATION1_NAME);
    }

    @Test
    public void addCard_locationExistInDb() {
        //location added to db in @before
        cardService.addCard(card);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addCard_LocationDoesntExistInDb() {
        //location added to db in @before
        card.setLocationId(new ObjectId().toHexString());
        cardService.addCard(card);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addCard_validUntilEarlierThenManufactured() {
        //location added to db in @before
        card.setValidUntil(LocalDateTime.now().toString());
        card.setManufactured(LocalDateTime.now().toString());
        cardService.addCard(card);
    }


    @Test
    public void updateCard_basic() {
        //location added to db in @before
        cardService.addCard(card);
        Card updateCard = new Card();
        updateCard.setId(card.getId());
        updateCard.setLocationId(location1.getId());
        updateCard.setManufactured(LocalDateTime.now().toString());
        updateCard.setValidUntil(CARD_VALID_UNTIL);
        String updatedSerial = "updatedSerial";
        updateCard.setSerialNumber(updatedSerial);

        cardService.updateCard(updateCard);
        assertThat(updateCard.getSerialNumber()).isEqualTo(cardService.getCardById(updateCard.getId()).getSerialNumber());
    }



    @Test(expected = ResourceNotFoundException.class)
    public void updateCard_updatableCardDoesntExist() {
        //location added to db in @before
        card.setId(new ObjectId().toHexString());
        cardService.updateCard(card);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void updateCard_throwsException_LocationDoesntExistInDb() {
        //location added to db in @before
        card.setLocationId(new ObjectId().toHexString());
        cardService.addCard(card);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void updateCard_throwsException_validUntilEarlierThenManufactured() {
        //location added to db in @before
        card.setValidUntil(LocalDateTime.now().toString());
        card.setManufactured(LocalDateTime.now().toString());
        cardService.addCard(card);
    }

    @Test
    public void deleteCardById() {
        cardService.addCard(card);
        Card secondCard = new Card("", "456", location1.getId(), LOCATION1_NAME, CARD_MANUFACTURED, CARD_VALID_UNTIL, CARD_TYPE);
        Card thirdCard = new Card("", "789", location1.getId(), LOCATION1_NAME, CARD_MANUFACTURED, CARD_VALID_UNTIL, CARD_TYPE);
        cardService.addCard(secondCard);
        cardService.addCard(thirdCard);

        cardService.deleteCardById(secondCard.getId());
        assertThat(cardService.getAllCards().stream().map(Card::getSerialNumber))
                .doesNotContain("456").contains("123456", "789");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCardById_whenCardDoesntExist() {
        cardService.deleteCardById(new ObjectId().toHexString());
    }

    @Test
    public void existById() {
        cardService.addCard(card);
        assertThat(cardService.existById(card.getId())).isEqualTo(true);
    }

    @Test
    public void existById_notInDb() {
        assertThat(cardService.existById(card.getId())).isEqualTo(false);
    }

    @Test
    public void existById_idNull() {
        assertThat(cardService.existById(null)).isEqualTo(false);
    }

    @Test
    public void doesntExistById() {
        assertThat(cardService.existById(new ObjectId().toHexString())).isEqualTo(false);
    }

    @Test
    public void cardExist() {
        cardService.addCard(card);
        assertThat(cardService.cardExist(card)).isEqualTo(true);
    }

    @Test
    public void cardDoestExist() {
        card.setId(new ObjectId().toHexString());
        assertThat(cardService.cardExist(card)).isEqualTo(false);

    }
}
