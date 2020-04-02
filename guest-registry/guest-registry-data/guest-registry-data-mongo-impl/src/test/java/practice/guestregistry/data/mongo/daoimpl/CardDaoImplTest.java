package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.mongo.EmbeddedMongoConfig;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.data.mongo.mappers.LocationMapper;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityDeletionException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LocationDao.class, LocationDaoImpl.class, LocationMapper.class,
                            CardDao.class, CardDaoImpl.class, CardMapper.class})
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
public class CardDaoImplTest {
    @Autowired
    public MongoTemplate mongoTemplate;
    @Autowired
    public CardDao cardDao;
    @Autowired
    public LocationDao locationDao;

    public Card card;
    public static final String CARD_SERIAL = "123456";
    public static final String CARD_TYPE = "PERSONNEL";
    public static final String CARD_MANUFACTURED = LocalDateTime.now().toString();
    public static final String CARD_VALID_UNTIL = "2022-03-25T22:57:00.795";

    public Location location;
    public static final String LOCATION1_NAME = "A";
    public static final String LOCATION1_COUNTRY = "Lietuva";
    public static final String LOCATION1_CITY = "Vilnius";
    public static final String LOCATION1_ADDRESS = "ZALGIRIO 90";
    public static final String LOCATION1_LOCATION_TYPE = "OFFICE";
    public static final String LOCATION1_PHONE_NUMBER = "851212345";

    @Before
    public void initTest() {
        cardDao.deleteAll();

        location = new Location();
        location.setName(LOCATION1_NAME);
        location.setCountry(LOCATION1_COUNTRY);
        location.setCity(LOCATION1_CITY);
        location.setAddress(LOCATION1_ADDRESS);
        location.setLocationType(LOCATION1_LOCATION_TYPE);
        location.setPhoneNumber(LOCATION1_PHONE_NUMBER);
        locationDao.add(location);

        card = new Card();
        card.setSerialNumber(CARD_SERIAL);
        card.setLocationId(location.getId());
        card.setLocationName(LOCATION1_NAME);
        card.setManufactured(CARD_MANUFACTURED);
        card.setValidUntil(CARD_VALID_UNTIL);
        card.setCtype(CARD_TYPE);
    }

    @Test(expected = EntityCreationException.class)
    public void add_expectException() {
        card.setId(ObjectId.get().toHexString());
        cardDao.add(card);
    }
    @Test
    public void findById_basic() {
        cardDao.add(card);
        assertThat(cardDao.findById(card.getId())).isEqualTo(card);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_cardDoesntExist() {
        cardDao.findById(ObjectId.get().toHexString());
    }

    @Test
    public void findAll() {
        cardDao.add(card);
        String firstId = card.getId();

        String secondSerial = "secondSerial";
        card.setId(null);
        card.setSerialNumber(secondSerial);
        cardDao.add(card);

        String thirdSerial = "thirdSerial";
        card.setId(null);
        card.setSerialNumber(thirdSerial);
        cardDao.add(card);

        assertThat(cardDao.findAll().size()).isEqualTo(3);
        assertThat(cardDao.findAll().stream().map(Card::getSerialNumber))
                .contains(CARD_SERIAL, secondSerial, thirdSerial);
    }


    @Test
    public void deleteById() {
        cardDao.add(card);
        cardDao.deleteById(card.getId());
        assertThat(cardDao.findAll().isEmpty()).isEqualTo(true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteById_cardDoesntExist() {
        cardDao.deleteById(ObjectId.get().toHexString());
    }

    @Test
    public void deleteAll() {
        cardDao.add(card);
        card.setId(null);
        card.setSerialNumber("secondSerial");
        cardDao.add(card);

        card.setId(null);
        card.setSerialNumber("thirdSerial");
        cardDao.add(card);

        assertThat(cardDao.findAll().size()).isEqualTo(3);
        cardDao.deleteAll();
        assertThat(cardDao.findAll().isEmpty()).isEqualTo(true);
    }


    @Test(expected = EntityUpdateException.class)
    public void update_idIsNull_expectException() {
        cardDao.add(card);
        card.setId(null);
        cardDao.update(card);
        assertThat(cardDao.findById(card.getId())).isEqualTo(card);
    }

//    public void deleteAll() {
//        String firstId = card.getId();
//
//        String secondId = ObjectId.get().toHexString();
//        String secondSerial = "secondSerial";
//        card.setId(secondId);
//        card.setSerialNumber(secondSerial);
//        cardDao.add(card);
//
//        String thirdId = ObjectId.get().toHexString();
//        String thirdSerial = "thirdSerial";
//        card.setId(thirdId);
//        card.setSerialNumber(thirdSerial);
//        cardDao.add(card);
//
//        cardDao.deleteAll();
//        assertThat(cardDao.findAll().isEmpty()).isEqualTo(true);
//
//    }
    @Test
    public void existById() {
        cardDao.add(card);
        assertThat(cardDao.existById(card.getId())).isEqualTo(true);
    }

    @Test
    public void existById_not() {
        assertThat(cardDao.existById(ObjectId.get().toHexString())).isEqualTo(false);
    }

    @Test
    public void exist() {
        cardDao.add(card);
        assertThat(cardDao.exist(card)).isEqualTo(true);
    }

    @Test
    public void exist_not() {
        card.setSerialNumber("newSerial");
        assertThat(cardDao.exist(card)).isEqualTo(false);
    }

    @Test
    public void existCardContainingIdSerial() {
        System.out.println(card);
        cardDao.add(card);
        assertThat(cardDao.existCardContainingIdSerial(card.getId(), card.getSerialNumber())).isEqualTo(true);
    }

    @Test
    public void existCardContainingIdSerial_cardDoesntExistInDb() {
        assertThat(cardDao.existCardContainingIdSerial(card.getId(), card.getSerialNumber())).isEqualTo(false);
    }

    @Test
    public void existCardContainingIdSerial_incorrectData() {
        assertThat(cardDao.existCardContainingIdSerial("someID", "serial")).isEqualTo(false);
    }

    @Test
    public void serialNumberExist() {
        cardDao.add(card);
        assertThat(cardDao.serialNumberExist(card.getSerialNumber())).isEqualTo(true);
    }

    @Test
    public void serialNumberExist_doesnt() {
        assertThat(cardDao.serialNumberExist(card.getSerialNumber())).isEqualTo(false);
    }
}
