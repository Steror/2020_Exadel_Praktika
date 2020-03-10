package practice.guestregistry.services_tests;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;
import practice.guestregistry.services.WorkerService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//@DataMongoTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
//@AutoConfigureMockMvc
// @ExtendWith(MockitoExtension.class)
@AutoConfigureDataMongo
@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//@DataMongoTest
public class WorkerServiceTests {
    @Autowired
    private WorkerService workerService;
    @Autowired
    MongoTemplate mongoTemplate;

//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }

//    @After
    @Before
    public void cleanUp() {
		for (String name : mongoTemplate.getCollectionNames()) {
			mongoTemplate.dropCollection(name);
		}
    }

    @Test
    public void add_worker_when_person_exist_in_db() {
        Person person = new Person(ObjectId.get(), "first", "second", "thidrd", "email@email.com", "111111");
        mongoTemplate.save(person);
        Worker worker = new Worker(ObjectId.get(), person, null);
        Worker savedWorker = workerService.addWorker(worker);

        long collectionSize = mongoTemplate.findAll(Worker.class).size();
        assertThat(savedWorker.getId()).isNotNull();
        assertThat(collectionSize).isEqualTo(1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void add_worker_when_person_dont_exist_in_Db() {
        Person person = new Person(ObjectId.get(),
                "first",
                "second",
                "thidrd",
                "email@email.com",
                "111111");
        workerService.addWorker(new Worker(ObjectId.get(), person, null));
    }

    @Test
    public void add_worker_when_card_exist_in_db() {
        Person person = new Person(ObjectId.get(), "first", "second", "thidrd", "email@email.com", "111111");
        mongoTemplate.save(person);
        Card card = new Card();
        card.setId(ObjectId.get());
        mongoTemplate.save(card);
        Worker worker = new Worker(ObjectId.get(), person, card);
        Worker savedWorker = workerService.addWorker(worker);

        long collectionSize = mongoTemplate.findAll(Worker.class).size();
        assertThat(savedWorker.getId()).isNotNull();
        assertThat(collectionSize).isEqualTo(1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void add_worker_when_card_dont_exist_in_Db() {
        Person person = new Person(ObjectId.get(),
                "first",
                "second",
                "thidrd",
                "email@email.com",
                "111111");
        Card card = new Card();
        card.setId(ObjectId.get());
        workerService.addWorker(new Worker(ObjectId.get(), person, card));
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void add_worker_when_person_null() {
        Worker worker = new Worker(ObjectId.get(), null, null);
        workerService.addWorker(worker);
    }

    @Test
    public void get_all_workers() {
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
        assertThat(workerService.getAllWorkers().size()).isEqualTo(3);
    }

    @Test
    public void get_existing_worker_by_id() {
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));

        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);
        assertThat(workerService.getWorkerById(savedWorker.getId()).get()).isEqualTo(savedWorker);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void get_not_existing_worker_by_id() {
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(3);

        workerService.getWorkerById(ObjectId.get());
    }



    @Test
    public void delete_existing_worker_by_id() {
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));

        workerService.deleteWorkerById(savedWorker.getId());
        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_not_existing_worker_by_id() {
        mongoTemplate.save(new Worker(ObjectId.get(), null, null));

        workerService.getWorkerById(ObjectId.get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dont_update_not_existing_id() {
        Worker savedWorker = mongoTemplate.save(new Worker(ObjectId.get(), null, null));
        savedWorker.setId(ObjectId.get());
        workerService.updateWorker(savedWorker);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_when_person_is_null() {
        Person person = new Person();
        person.setId(ObjectId.get());
        Worker savedWorker = workerService.addWorker(new Worker(ObjectId.get(), person, null));
        workerService.updateWorker(new Worker(ObjectId.get(), null, null));
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_when_person_is_not_in_db() {
        Person person = new Person();
        person.setId(ObjectId.get());
        mongoTemplate.save(person);
        Worker savedWorker = workerService.addWorker(new Worker(null, person, null));
        Person personNotInDb = new Person();
        // because person id @NotNull
        personNotInDb.setId(ObjectId.get());
        workerService.updateWorker(new Worker(savedWorker.getId(), personNotInDb, null));
    }

    @Test
    public void update_when_person_is_in_db_correct() {
        //adding person
        Person person = new Person();
        person.setId(ObjectId.get());
        Person savedPerson = mongoTemplate.save(person);
        Worker savedWorker = workerService.addWorker(new Worker(null, person, null));

        //creating new person to update, and add him to db
        Person updatingWithPerson = new Person();
        updatingWithPerson.setId(ObjectId.get());
        mongoTemplate.save(updatingWithPerson);

        // updating to new Worker
        Worker newWorker = new Worker(savedWorker.getId(), updatingWithPerson, null);
        workerService.updateWorker(newWorker);

        System.out.println(mongoTemplate.findAll(Worker.class));
        assertThat(mongoTemplate.findAll(Worker.class).size()).isEqualTo(1);
        assertThat(newWorker).isEqualTo(mongoTemplate.findAll(Worker.class).get(0));
    }



    //sitas ir neturi veikti, nes servise validacija bus paleista anksciau, nei cascadingSave issaugos person db
    @Test
    public void cascadeSave_on_saving() {
//        ObjectId dummyId = new ObjectId();
//        Person person = new Person(dummyId,
//                "first",
//                "second",
//                "thidrd",
//                "email@email.com",
//                "111111");
//        Card card = new Card();
//        card.setSerialNumber("a");
//        card.setCtype(CardType.PERSONNEL);
//        card.setManufactured(LocalDateTime.now());
//        card.setValidUntil(LocalDateTime.now());
//        Worker worker =  new Worker(null, person, card);
//        workerService.addWorker(worker);
//
//        List<Person> personInDb = mongoTemplate.find(Query.query(Criteria.where("firstName").is(person.getFirstName())),Person.class);
//        System.out.println(personInDb.get(0));
//        assertThat(personInDb.size()).isEqualTo(1);
    }

    @Test
    public void cascadeSave_on_updating() {
        ObjectId dummyId = new ObjectId();
        Person person = new Person(dummyId,
                "first",
                "second",
                "thidrd",
                "email@email.com",
                "111111");
        Worker worker =  new Worker(null, person, null);
        mongoTemplate.save(person);
        Worker savedWorker = workerService.addWorker(worker);

        person.setLastName("LAST");
        workerService.updateWorker(worker);

        List<Worker> personInDb = mongoTemplate.find(Query.query(Criteria.where("id").is(savedWorker.getId())), Worker.class);

        System.out.println(personInDb.get(0));
        assertThat(workerService.getWorkerById(savedWorker.getId()).get().getPerson().getLastName()).isEqualTo("LAST");
    }
}
