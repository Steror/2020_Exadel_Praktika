//package practice.guestregistry.services.impl.integrationtest;
//
//import org.bson.types.ObjectId;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import practice.guestregistry.data.api.dao.CardDao;
//import practice.guestregistry.data.api.dao.LocationDao;
//import practice.guestregistry.data.api.dao.PersonDao;
//import practice.guestregistry.data.api.dao.WorkerDao;
//import practice.guestregistry.data.mongo.daoimpl.CardDaoImpl;
//import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
//import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
//import practice.guestregistry.data.mongo.daoimpl.WorkerDaoImpl;
//import practice.guestregistry.data.mongo.entities.CardEntity;
//import practice.guestregistry.data.mongo.entities.LocationEntity;
//import practice.guestregistry.data.mongo.entities.PersonEntity;
//import practice.guestregistry.data.mongo.entities.WorkerEntity;
//import practice.guestregistry.data.mongo.listeners.CascadeSaveMongoEventListener;
//import practice.guestregistry.data.mongo.mappers.CardMapper;
//import practice.guestregistry.data.mongo.mappers.LocationMapper;
//import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
//import practice.guestregistry.data.mongo.mappers.WorkerMapper;
//import practice.guestregistry.domain.Card;
//import practice.guestregistry.domain.Location;
//import practice.guestregistry.domain.Person;
//import practice.guestregistry.domain.Worker;
//import practice.guestregistry.exceptions.InvalidDocumentStateException;
//import practice.guestregistry.services.EmbeddedMongoConfig;
//import practice.guestregistry.services.service.CardService;
//import practice.guestregistry.services.service.WorkerService;
//import practice.guestregistry.services.serviceimpl.CardServiceImpl;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {
//        WorkerDao.class, WorkerDaoImpl.class, WorkerMapper.class,
//        LocationDao.class, LocationDaoImpl.class, LocationMapper.class,
//        PersonDao.class, PersonDaoImpl.class, PersonDomainEntityMapper.class,
//        CardService.class, CardServiceImpl.class, CardDao.class, CardDaoImpl.class, CardMapper.class,
//        CascadeSaveMongoEventListener.class
//})
//@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
//public class WorkerServiceImplTests {
//    @Autowired
//    public WorkerService workerService;
//    @Autowired
//    public CardService cardService;
//    @Autowired
//    public MongoTemplate mongoTemplate;
//    @Autowired
//    public WorkerMapper mapper;
//    @Autowired
//    public LocationDao locationDao;
//    @Autowired
//    public PersonDao personDao;
////    @Before
////    public void init() {
////        MockitoAnnotations.initMocks(this);
////    }
//
//    public Worker worker;
//    public Person person;
//    public static final String PERSON_FIRST_NAME = "P1 FIRST";
//    public static final String PERSON_MIDDLE_NAME = "P1 midl name";
//    public static final String PERSON_LAST_NAME = "P1 Last anem";
//    public static final String PERSON_EMAIL = "person1@email.com";
//    public static final String PERSON_PHONE_NUMBER = "123";
//
//    public Card card;
//    public static final String CARD_SERIAL = "123456";
//    public static final String CARD_TYPE = "PERSONNEL";
//    public static final String CARD_MANUFACTURED = LocalDateTime.now().toString();
//    public static final String CARD_VALID_UNTIL = "2022-03-25T22:57:00.795";
//
//    public Location location;
//    public static final String LOCATION1_NAME = "A";
//    public static final String LOCATION1_COUNTRY = "Lietuva";
//    public static final String LOCATION1_CITY = "Vilnius";
//    public static final String LOCATION1_ADDRESS = "ZALGIRIO 90";
//    public static final String LOCATION1_LOCATION_TYPE = "OFFICE";
//    public static final String LOCATION1_PHONE_NUMBER = "851212345";
//
//    @Before
//    public void initTest() {
//        mongoTemplate.remove(CardEntity.class);
//        mongoTemplate.remove(WorkerEntity.class);
//        mongoTemplate.remove(PersonEntity.class);
//        mongoTemplate.remove(LocationEntity.class);
////        workerDao.deleteAll();
////        personDao.deleteAll();
////        cardDao.deleteAll();
////        locationDao.deleteAll();
//
//        location = new Location();
//        location.setName(LOCATION1_NAME);
//        location.setCountry(LOCATION1_COUNTRY);
//        location.setCity(LOCATION1_CITY);
//        location.setAddress(LOCATION1_ADDRESS);
//        location.setLocationType(LOCATION1_LOCATION_TYPE);
//        location.setPhoneNumber(LOCATION1_PHONE_NUMBER);
//        locationDao.add(location);
//
//        card = new Card();
//        card.setSerialNumber(CARD_SERIAL);
//        card.setCtype(CARD_TYPE);
//        card.setManufactured(CARD_MANUFACTURED);
//        card.setValidUntil(CARD_VALID_UNTIL);
//        card.setLocationId(location.getId());
//        card.setLocationName(LOCATION1_PHONE_NUMBER);
//        cardService.addCard(card);
//
//        person = new Person();
//        person.setFirstName(PERSON_FIRST_NAME);
//        person.setMiddleName(PERSON_MIDDLE_NAME);
//        person.setLastName(PERSON_LAST_NAME);
//        person.setEmail(PERSON_EMAIL);
//        person.setPhoneNumber(PERSON_PHONE_NUMBER);
//        personDao.add(person);
//
//        worker = new Worker();
//        worker.setId(null);
//        worker.setPersonId(person.getId());
////        worker.setFirstName(null);
//        worker.setFirstName(PERSON_FIRST_NAME);
//        worker.setMiddleName(PERSON_MIDDLE_NAME);
//        worker.setLastName(PERSON_LAST_NAME);
//        worker.setEmail(PERSON_EMAIL);
//        worker.setPhoneNumber(PERSON_PHONE_NUMBER);
//        worker.setCardId(card.getId());
//        worker.setCardSerialNumber(CARD_SERIAL);
//    }
//
//    @Test
//    public void addWorker_IdNull() {
//        workerService.addWorker(worker);
//        assertThat(workerService.getWorkerById(worker.getId())).isEqualTo(worker);
//    }
//
//    @Test
//    public void addWorker_personExistInDb() {
//        workerService.addWorker(worker);
//
//        long personCount = personDao.findAll().size();
//        long workerCount = workerService.getAllWorkers().size();
//        assertThat(personCount).isEqualTo(1);
//        assertThat(workerCount).isEqualTo(1);
//    }
//
////    @Test(expected = InvalidDocumentStateException.class)
//    @Test
//    public void addWorker_personDontExistInDb() {
//        //this should create new person ,because full name distinct
//        worker.setFirstName("name");
//        workerService.addWorker(worker);
//
//        long personCount = personDao.findAll().size();
//        long workerCount = workerService.getAllWorkers().size();
//        assertThat(personCount).isEqualTo(2);
//        assertThat(workerCount).isEqualTo(2);
//    }
//
//    @Test
//    public void addWorker_whenCardNull() {
//        worker.setCardId(null);
//        workerService.addWorker(worker);
//
//        long personCount = personDao.findAll().size();
//        long workerCount = workerService.getAllWorkers().size();
//        assertThat(personCount).isEqualTo(1);
//        assertThat(workerCount).isEqualTo(1);
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void addWorker_CardDoesntExistInDb() {
//        worker.setCardId("someId");
//        workerService.addWorker(worker);
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void addWorkerWhenPersonNull() {
//        worker.setFirstName(null);
//        worker.setMiddleName(null);
//        worker.setLastName(null);
//        workerService.addWorker(worker);
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void add_worker_when_person_null() {
//        worker.setFirstName(null);
//        worker.setMiddleName(null);
//        worker.setLastName(null);
//        workerService.addWorker(worker);
//    }
//
//    @Test
//    public void get_all_workers() {
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
//        assertThat(workerService.getAllWorkers().size()).isEqualTo(3);
//    }
//
//    @Test
//    public void get_existing_worker_by_id() {
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
////        assertThat(workerService.getWorkerById(savedWorker.getId()).get()).isEqualTo(savedWorker);
//        assertThat(workerService.getWorkerById(savedWorker.getId()).get()).isEqualTo(mapper.map(savedWorker));
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void get_not_existing_worker_by_id() {
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
//
//        workerService.getWorkerById(ObjectId.get());
//    }
//
//
//
//    @Test
//    public void delete_existing_worker_by_id() {
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//
//        workerService.deleteWorkerById(savedWorker.getId());
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(2);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void delete_not_existing_worker_by_id() {
//        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//
//        workerService.getWorkerById(ObjectId.get());
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void dont_update_not_existing_id() {
//        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));
//        savedWorker.setId(ObjectId.get());
//        workerService.updateWorker(mapper.map(savedWorker));
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void update_when_person_is_null() {
//        Person person = new Person();
//        person.setId(ObjectId.get());
//        WorkerDTO savedWorker = workerService.addWorker(mapper.map(new Worker(ObjectId.get(), person, null)));
//        workerService.updateWorker(mapper.map(new Worker(new ObjectId(savedWorker.getWorkerId()), null, null)));
//    }
//
//    @Test(expected = InvalidDocumentStateException.class)
//    public void update_when_person_is_not_in_db() {
//        Person person = new Person();
//        person.setId(ObjectId.get());
//        mongoTemplate.save(person);
//        WorkerDTO savedWorker = workerService.addWorker(mapper.map(new Worker(null, person, null)));
//        Person personNotInDb = new Person();
//        // because person id @NotNull
//        personNotInDb.setId(ObjectId.get());
//        workerService.updateWorker(mapper.map(new Worker(new ObjectId(savedWorker.getWorkerId()), personNotInDb, null)));
//    }
//
//    @Test
//    public void update_when_person_is_in_db_correct() {
//        //adding person
//        Person person = new Person();
//        person.setId(ObjectId.get());
//        Person savedPerson = mongoTemplate.save(person);
//        WorkerDTO savedWorker = workerService.addWorker(mapper.map(new Worker(null, person, null)));
//
//        //creating new person to update, and add him to db
//        Person updatingWithPerson = new Person();
//        updatingWithPerson.setId(ObjectId.get());
//        Person savedPerson1 = mongoTemplate.save(updatingWithPerson);
//
////        System.out.println(updatingWithPerson);
////        System.out.println(savedPerson1);
//        System.out.println(mongoTemplate.exists(Query.query(Criteria.byExample(savedPerson1)), Person.class));
//        // updating to new Worker
//        WorkerDTO newWorker = mapper.map(new Worker(new ObjectId(savedWorker.getWorkerId()), updatingWithPerson, null));
//        workerService.updateWorker(newWorker);
//
//        System.out.println(mongoTemplate.findAll(Worker.class));
//        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(1);
//        assertThat(newWorker).isEqualTo(mapper.map(mongoTemplate.findAll(Worker.class).get(0)));
//    }
//
//
//
//    //sitas ir neturi veikti, nes servise validacija bus paleista anksciau, nei cascadingSave issaugos person db
////    @Test
////    public void cascadeSave_on_saving() {
////        ObjectId dummyId = new ObjectId();
////        Person person = new Person(dummyId,
////                "first",
////                "second",
////                "thidrd",
////                "email@email.com",
////                "111111");
////        Card card = new Card();
////        card.setSerialNumber("a");
////        card.setCtype(CardType.PERSONNEL);
////        card.setManufactured(LocalDateTime.now());
////        card.setValidUntil(LocalDateTime.now());
////        Worker worker =  new Worker(null, person, card);
////        workerService.addWorker(mapper.map(worker));
////
////        List<Person> personInDb = mongoTemplate.find(Query.query(Criteria.where("firstName").is(person.getFirstName())),Person.class);
////        System.out.println(personInDb.get(0));
////        assertThat(personInDb.size()).isEqualTo(1);
////    }
//
//    @Test
//    public void cascadeSave_on_updating() {
//        ObjectId dummyId = new ObjectId();
//        Person person = new Person(dummyId,
//                "first",
//                "second",
//                "third",
//                "email@email.com",
//                "111111");
//        Person savedPerson = mongoTemplate.save(person);
//        WorkerDTO savedWorker = workerService.addWorker(mapper.map(new Worker(null, savedPerson, null)));
//        savedWorker.setLastName("LAST");
//
//        Worker test = mapper.map(savedWorker);
////        System.out.println("person from mapped" + test.getPerson());
//        System.out.println(mongoTemplate.exists(Query.query(Criteria.byExample(test.getPerson())), Person.class));
////        System.out.println("test" + savedWorker);
//        workerService.updateWorker(savedWorker);
//
//        List<Worker> personInDb = mongoTemplate.find(Query.query(Criteria.where("id").is(new ObjectId(savedWorker.getWorkerId()))), Worker.class);
//
//        System.out.println(personInDb.get(0));
//        assertThat(workerService.getWorkerById(new ObjectId(savedWorker.getWorkerId())).get().getLastName()).isEqualTo("LAST");
//    }
//
//}
