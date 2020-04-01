package practice.guestregistry.services.impl.integrationtest;

import org.bson.types.ObjectId;
import org.junit.AfterClass;
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
import practice.guestregistry.data.mongo.daoimpl.CardDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.WorkerDaoImpl;
import practice.guestregistry.data.mongo.mappers.*;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.domain.Person;
import practice.guestregistry.domain.Worker;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.EmbeddedMongoConfig;
import practice.guestregistry.services.service.CardService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.service.WorkerService;
import practice.guestregistry.services.serviceimpl.CardServiceImpl;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;
import practice.guestregistry.services.serviceimpl.PersonServiceImpl;
import practice.guestregistry.services.serviceimpl.WorkerServiceImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        WorkerService.class, WorkerServiceImpl.class, WorkerDao.class, WorkerDaoImpl.class, WorkerMapper.class,
        LocationService.class, LocationServiceImpl.class, LocationDao.class, LocationDaoImpl.class, LocationMapper.class,
        PersonService.class, PersonServiceImpl.class, PersonDao.class, PersonDaoImpl.class, PersonDomainEntityMapper.class,
        CardService.class, CardServiceImpl.class, CardDao.class, CardDaoImpl.class, CardMapper.class,
        WorkerDomainToPersonDomainMapper.class
})
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
public class WorkerServiceImplTests {
    @Autowired
    public WorkerService workerService;
    @Autowired
    public CardService cardService;
    @Autowired
    public MongoTemplate mongoTemplate;
    @Autowired
    public WorkerMapper mapper;
    @Autowired
    public LocationDao locationDao;
    @Autowired
    public PersonDao personDao;

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
        workerService.deleteAllWorkers();
        personDao.deleteAll();
        cardService.deleteAllCards();
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
        card.setLocationName(LOCATION1_NAME);
        cardService.addCard(card);

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
    public void addWorker_IdNull() {
        workerService.addWorker(worker);
        assertThat(workerService.getWorkerById(worker.getId())).isEqualTo(worker);
    }

    @Test
    public void addWorker_personExistInDb() {
        workerService.addWorker(worker);

        long personCount = personDao.findAll().size();
        long workerCount = workerService.getAllWorkers().size();
        assertThat(personCount).isEqualTo(1);
        assertThat(workerCount).isEqualTo(1);
    }

    @Test
    public void add2Workers_sharingSamePerson() {
        workerService.addWorker(worker);
        worker.setId(null);
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(2);
        assertThat(personDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void add3Workers_sharingSamePerson() {
        workerService.addWorker(worker);
        worker.setId(null);
        workerService.addWorker(worker);
        worker.setId(null);
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(3);
        assertThat(personDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void add3Workers_2Persons() {
        workerService.addWorker(worker);
        worker.setId(null);
        workerService.addWorker(worker);
        worker.setId(null);
        worker.setLastName("newLastName");
        worker.setEmail("new@email.com");
        worker.setPhoneNumber("33333333");
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(3);
        assertThat(personDao.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteWorker_2WorkerssharingSamePerson() {
        workerService.addWorker(worker);
        String firstId = worker.getId();
        worker.setId(null);
        workerService.addWorker(worker);

        workerService.deleteWorkerById(firstId);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(1);
        assertThat(personDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void delete2Worker_bothShareSamePerson() {
        workerService.addWorker(worker);
        String firstId = worker.getId();
        worker.setId(null);
        workerService.addWorker(worker);

        workerService.deleteWorkerById(firstId);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(1);
        assertThat(personDao.findAll().size()).isEqualTo(1);

        workerService.deleteWorkerById(worker.getId());
        assertThat(workerService.getAllWorkers().size()).isEqualTo(0);
        assertThat(personDao.findAll().size()).isEqualTo(0);
    }

    @Test
    public void addWorker_personDontExistInDb() {
        //this should create new person ,because full name distinct
        worker.setFirstName("name");
        worker.setEmail("newEmail@com.com");
        worker.setPhoneNumber("222222");
        workerService.addWorker(worker);

        long personCount = personDao.findAll().size();
        long workerCount = workerService.getAllWorkers().size();
        assertThat(personCount).isEqualTo(2); //first added by init
        assertThat(workerCount).isEqualTo(1);
    }

    @Test(expected = EntityCreationException.class)
    public void addWorker_newPersonWithExistingEmail() {
        //this should create new person ,because full name distinct
        worker.setFirstName("enw@email.com");
        worker.setPhoneNumber("22222222");
        workerService.addWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addWorker_whenCardNull() {
        worker.setCardId(null);
        workerService.addWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addWorker_CardDoesntExistInDb() {
        worker.setCardId("someId");
        workerService.addWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addWorker_PersonFullNameNull() {
        personDao.deleteAll();
        worker.setFirstName(null);
        worker.setMiddleName(null);
        worker.setLastName(null);
        workerService.addWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addWorker_emailEmpty() {
        personDao.deleteAll();
        worker.setEmail("");
        workerService.addWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addWorker_emailNull() {
        personDao.deleteAll();
        worker.setEmail(null);
        workerService.addWorker(worker);
    }

    @Test
    public void findById() {
        workerService.addWorker(worker);
        assertThat(workerService.getWorkerById(worker.getId())).isEqualTo(worker);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_idNull() {
        workerService.getWorkerById(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_idEmpty() {
        workerService.getWorkerById(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_idValidIncorrect() {
        workerService.getWorkerById(ObjectId.get().toHexString());
    }

    @Test
    public void findAllWorkers() {
        workerService.addWorker(worker);
        worker.setId(null);
        worker.setFirstName("newFirst");
        worker.setEmail("second@email.com");
        worker.setPhoneNumber("22222222");
        workerService.addWorker(worker);
        worker.setId(null);
        worker.setMiddleName("newMiddle");
        worker.setEmail("third@email.com");
        worker.setPhoneNumber("33333333");
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(3);
        assertThat(personDao.findAll().size()).isEqualTo(3);
    }

    @Test
    public void deleteWorkerById() {
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(1);
        workerService.deleteWorkerById(worker.getId());
        assertThat(workerService.getAllWorkers().size()).isEqualTo(0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteWorkerById_idNull() {
        workerService.getWorkerById(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteWorkerById_idInvalid() {
        workerService.getWorkerById("nelenkoks");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteWorkerById_valid_workerDoesntExist() {
        workerService.getWorkerById(ObjectId.get().toHexString());
    }

    @Test
    public void workerExistById() {
        workerService.addWorker(worker);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(1);
        assertThat(workerService.existById(worker.getId())).isEqualTo(true);
    }

    public void workerExistById_idNull() {
        assertThat(workerService.existById(null)).isEqualTo(false);
    }

    @Test
    public void workerExistById_idInvalid() {
        assertThat(workerService.existById("belenkoks")).isEqualTo(false);
    }

    @Test
    public void workerExistById_idValid_workerNotSaved() {
        assertThat(workerService.existById(ObjectId.get().toHexString())).isEqualTo(false);
    }

    @Test
    public void workerExist() {
        workerService.addWorker(worker);
        assertThat(workerService.exist(worker)).isEqualTo(true);
    }

    @Test
    public void workerExist_workerNotInDb() {
        assertThat(workerService.exist(worker)).isEqualTo(false);
    }

    @Test
    public void workerUpdate() {
        workerService.addWorker(worker);
        worker.setEmail("updatedEmail@com.com");
        workerService.updateWorker(worker);
        assertThat(workerService.getWorkerById(worker.getId())).isEqualTo(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void workerUpdate_whenPersonIdNull() {
        workerService.addWorker(worker);
        worker.setPersonId(null);
        workerService.updateWorker(worker);
        assertThat(workerService.getWorkerById(worker.getId())).isEqualTo(worker);
    }


    @Test(expected = EntityUpdateException.class)
    public void workerUpdate_whenUpdatingToExistingEmail() {
        workerService.addWorker(worker);
        String firstId = worker.getId();
        worker.setId(null);
        worker.setFirstName("newFirst");
        worker.setPhoneNumber("222222");
        worker.setEmail("newEmail@com.com");
        workerService.addWorker(worker);
        String secondId = worker.getId();

        worker.setEmail(PERSON_EMAIL);
        workerService.updateWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void workerUpdate_whenUpdatingToExistingPhoneNumber() {
        workerService.addWorker(worker);
        String firstId = worker.getId();
        worker.setFirstName("newFirst");
        worker.setPhoneNumber("222222");
        worker.setEmail("newEmail@com.com");
        worker.setId("");
        workerService.addWorker(worker);
        String secondId = worker.getId();

        worker.setEmail(PERSON_PHONE_NUMBER);
        workerService.updateWorker(worker);
    }

    @Test(expected = EntityUpdateException.class)
    public void workerUpdate_whenUpdatingToExistingName() {
        workerService.addWorker(worker);
        String firstId = worker.getId();
        worker.setFirstName("newFirst");
        worker.setPhoneNumber("222222");
        worker.setEmail("newEmail@com.com");
        worker.setId("");
        workerService.addWorker(worker);
        String secondId = worker.getId();

        worker.setFirstName(PERSON_FIRST_NAME);
        worker.setMiddleName(PERSON_MIDDLE_NAME);
        worker.setLastName(PERSON_LAST_NAME);
        workerService.updateWorker(worker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void workerUpdate_whenPersonDoenstExistInDb() {
        workerService.addWorker(worker);
        personDao.deleteAll();

        worker.setFirstName("newFirst");
        worker.setPhoneNumber("222222");
        worker.setEmail("newEmail@com.com");
        workerService.updateWorker(worker);
    }
//    @AfterClass
//    public static void shutdown() {
//        EmbeddedMongoConfig.stop();
//    }
}
