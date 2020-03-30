package practice.guestregistry.data.mongo.daoimpl;


import org.bson.types.ObjectId;
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
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.data.mongo.EmbeddedMongoConfig;
import practice.guestregistry.data.mongo.listeners.CascadeSaveMongoEventListener;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.data.mongo.mappers.LocationMapper;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.data.mongo.mappers.WorkerMapper;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.domain.Person;
import practice.guestregistry.domain.Worker;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        WorkerDao.class, WorkerDaoImpl.class, WorkerMapper.class,
        LocationDao.class, LocationDaoImpl.class, LocationMapper.class,
        PersonDao.class, PersonDaoImpl.class, PersonDomainEntityMapper.class,
        CardDao.class, CardDaoImpl.class, CardMapper.class,
        CascadeSaveMongoEventListener.class
})
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
public class WorkerDaoImplTests {

    @Autowired
    public WorkerDao workerDao;
    @Autowired
    public LocationDao locationDao;
    @Autowired
    public PersonDao personDao;
    @Autowired
    public CardDao cardDao;
    @Autowired
    public MongoTemplate mongoTemplate;

    public Worker worker;
    public Person person;
    public static final String PERSON_FIRST_NAME = "P1 FIRST";
    public static final String PERSON_MIDDLE_NAME = "P1 midl name";
    public static final String PERSON_LAST_NAME = "P1 Last anem";
    public static final String PERSON_EMAIL = "person1@email.com";
    public static final String PERSON_PHONE_NUMBER = "123";

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
        workerDao.deleteAll();
        personDao.deleteAll();
        cardDao.deleteAll();
        locationDao.deleteAll();

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
        card.setCtype(CARD_TYPE);
        card.setManufactured(CARD_MANUFACTURED);
        card.setValidUntil(CARD_VALID_UNTIL);
        card.setLocationId(location.getId());
        card.setLocationName(LOCATION1_PHONE_NUMBER);
        cardDao.add(card);

        person = new Person();
        person.setFirstName(PERSON_FIRST_NAME);
        person.setMiddleName(PERSON_MIDDLE_NAME);
        person.setLastName(PERSON_LAST_NAME);
        person.setEmail(PERSON_EMAIL);
        person.setPhoneNumber(PERSON_PHONE_NUMBER);
        personDao.add(person);

        worker = new Worker();
        worker.setId(null);
        worker.setPersonId(person.getId());
//        worker.setFirstName(null);
        worker.setFirstName(PERSON_FIRST_NAME);
        worker.setMiddleName(PERSON_MIDDLE_NAME);
        worker.setLastName(PERSON_LAST_NAME);
        worker.setEmail(PERSON_EMAIL);
        worker.setPhoneNumber(PERSON_PHONE_NUMBER);
        worker.setCardId(card.getId());
        worker.setCardSerialNumber(CARD_SERIAL);
    }

    @Test
    public void add() {
        workerDao.add(worker);
        assertThat(workerDao.findById(worker.getId())).isEqualTo(worker);
    }

    @Test(expected = EntityCreationException.class)
    public void add_idNotNull() {
        worker.setId(ObjectId.get().toHexString());
        workerDao.add(worker);
    }

    @Test
    public void update() {
        workerDao.add(worker);
        worker.setFirstName("new firstname");
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        workerDao.update(worker);
        assertThat(workerDao.findById(worker.getId())).isEqualTo(worker);
    }

    @Test(expected = EntityUpdateException.class)
    public void update_whenIdNull() {
        workerDao.add(worker);
        worker.setId(null);
        workerDao.update(worker);
        assertThat(workerDao.findById(worker.getId())).isEqualTo(worker);
    }

    @Test
    public void deleteById() {
        workerDao.add(worker);
        workerDao.deleteById(worker.getId());
        assertThat(workerDao.findAll().isEmpty());
    }

    @Test
    public void deleteById_whenIdNull() {
        workerDao.deleteById(null);
    }

    @Test
    public void deleteById_whenIncorrect() {
        workerDao.deleteById(null);
    }

    @Test
    public void deleteById_whenValidNotExisting() {
        workerDao.deleteById(new ObjectId().toHexString());
    }

    @Test
    public void exist() {
        workerDao.add(worker);
        assertThat(workerDao.exist(worker)).isEqualTo(true);
    }

    @Test
    public void exist_workerDontExist() {
        assertThat(workerDao.exist(worker)).isEqualTo(false);
    }

    @Test
    public void existById() {
        workerDao.add(worker);
        assertThat(workerDao.existById(worker.getId())).isEqualTo(true);
    }

    @Test
    public void existById_idNull() {
        assertThat(workerDao.existById(null)).isEqualTo(false);
    }

    @Test
    public void existById_incorrectId() {
        assertThat(workerDao.existById("belenkoks")).isEqualTo(false);
    }
}
