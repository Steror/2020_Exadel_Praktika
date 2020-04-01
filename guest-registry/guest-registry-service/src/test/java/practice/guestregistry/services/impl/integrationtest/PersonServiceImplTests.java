package practice.guestregistry.services.impl.integrationtest;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import org.bson.types.ObjectId;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.EmbeddedMongoConfig;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.serviceimpl.PersonServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.FILE;

@SpringBootTest(classes = {PersonServiceImpl.class, PersonDaoImpl.class, PersonDao.class, PersonDomainEntityMapper.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
//@AutoConfigureDataMongo
@ActiveProfiles("test")
public class PersonServiceImplTests {

    @Autowired
    PersonService personService;
    @Autowired
    MongoTemplate template;

    public Person person1;
    public Person person2;

    public static final String PERSON1_FIRST_NAME = "P1 FIRST";
    public static final String PERSON1_MIDDLE_NAME = "P1 midl name";
    public static final String PERSON1_LAST_NAME = "P1 Last anem";
    public static final String PERSON1_EMAIL = "person1@email.com";
    public static final String PERSON1_PHONE_NUMBER = "123";
    public static final String PERSON2_FIRST_NAME = "P2 FIRST";
    public static final String PERSON2_MIDDLE_NAME = "P2 midl name";
    public static final String PERSON2_LAST_NAME = "P2 Last anem";
    public static final String PERSON2_EMAIL = "person2@email.com";
    public static final String PERSON2_PHONE_NUMBER = "456";


    @Before
    public void init() {
        personService.deleteAllPersons();

        person1 = new Person();
//        person1.setId(ObjectId.get().toHexString());
        person1.setFirstName(PERSON1_FIRST_NAME);
        person1.setMiddleName(PERSON1_MIDDLE_NAME);
        person1.setLastName(PERSON1_LAST_NAME);
        person1.setEmail(PERSON1_EMAIL);
        person1.setPhoneNumber(PERSON1_PHONE_NUMBER);

        person2 = new Person();
        person2.setFirstName(PERSON2_FIRST_NAME);
        person2.setMiddleName(PERSON2_MIDDLE_NAME);
        person2.setLastName(PERSON2_LAST_NAME);
        person2.setEmail(PERSON2_EMAIL);
        person2.setPhoneNumber(PERSON2_PHONE_NUMBER);
    }

    @Test
    public void add_and_getPersonById_basic() {
        personService.addPerson(person1);
        assertThat(personService.getPersonById(person1.getId())).isEqualTo(person1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPersonById_idNull() {
        personService.getPersonById(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPersonById_invalidId() {
        personService.getPersonById("belenkoks");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPersonById_correctNotExistingId() {
        personService.getPersonById(ObjectId.get().toHexString());
    }
//
//
    @Test(expected = EntityCreationException.class)
    public void addPerson_existByFullName() {
        personService.addPerson(person1);
        person2.setFirstName(PERSON1_FIRST_NAME);
        person2.setMiddleName(PERSON1_MIDDLE_NAME);
        person2.setLastName(PERSON1_LAST_NAME);
        personService.addPerson(person2);
    }

//    @Test(expected = DomainCreationException.class)
    @Test(expected = EntityCreationException.class)
    public void addPerson_existByEmail() {
        personService.addPerson(person1);
        person2.setPhoneNumber(PERSON1_PHONE_NUMBER);
        System.out.println("\n\n\n" + person1);
        System.out.println("\n\n\n" + person2 + "\n\n\n");
        personService.getAllPersons().stream().forEach(System.out::println);
        personService.addPerson(person2);
//        template.getDb().getCollection("personEntity").listIndexes().forEach(System.out::println);
        template.getCollectionNames().forEach(System.out::println);
        template.indexOps(PersonEntity.class).getIndexInfo().stream().forEach(System.out::println);
        boolean shit = false;
        assertThat(shit).isEqualTo(false);
        personService.getAllPersons().stream().forEach(System.out::println);
//                    forEach(elem -> {
//            return System.out.println(elem.toString());
//        });
    }

    @Test
    public void getAllPersons() {
        personService.addPerson(person1);
        personService.addPerson(person2);
        assertThat(personService.getAllPersons().size()).isEqualTo(2);
        assertThat(personService.getAllPersons().stream().map(Person::getEmail)).contains(PERSON1_EMAIL, PERSON2_EMAIL);
    }

    @Test(expected = EntityCreationException.class)
    public void addPerson_existByPhoneNumber() {
        personService.addPerson(person1);
        person2.setPhoneNumber(PERSON1_PHONE_NUMBER);
        personService.addPerson(person2);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addPerson_phoneNumberContainsLetters() {
        person1.setPhoneNumber("12a3");
        personService.addPerson(person1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void addPerson_emailWithOutAt() {
        person2.setEmail("email.com");
        personService.addPerson(person2);
    }

    @Test
    public void updatePerson () {
        personService.addPerson(person1);
        person2.setId(person1.getId());
        person2.setFirstName("any name");
        personService.updatePerson(person2);
        assertThat(personService.getPersonById(person1.getId())).isEqualTo(person2);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void updatePerson_whenPhoneNumberIncorrect () {
        personService.addPerson(person1);
        person2.setId(person1.getId());
        person2.setPhoneNumber("123a5");
        personService.updatePerson(person2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatePerson_whenIdNull () {
        personService.addPerson(person1);
        person2.setId(null);
        personService.updatePerson(person2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatePerson_whenIdIncorrect () {
        personService.addPerson(person1);
        person2.setId("belenkoks");
        personService.updatePerson(person2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatePerson_whenIdPersonDoesntExist() {
        personService.addPerson(person1);
        personService.updatePerson(person2);
    }
//
    @Test(expected = EntityUpdateException.class)
    public void updatePerson_existByFullName() {
        personService.addPerson(person1);
        personService.addPerson(person2);
        person2.setFirstName(PERSON1_FIRST_NAME);
        person2.setMiddleName(PERSON1_MIDDLE_NAME);
        person2.setLastName(PERSON1_LAST_NAME);
        personService.updatePerson(person2);
    }

    @Test(expected = EntityUpdateException.class)
    public void updatePerson_existByEmail() {
        personService.addPerson(person1);
        personService.addPerson(person2);
        person2.setEmail(PERSON1_EMAIL);
        personService.updatePerson(person2);
    }

@Test(expected = EntityUpdateException.class)
    public void updatePerson_existByPhoneNumber() {
        personService.addPerson(person1);
        personService.addPerson(person2);
        person2.setPhoneNumber(PERSON1_PHONE_NUMBER);
        personService.updatePerson(person2);
    }


    @Test
    public void deleteAllPersons () {
        personService.addPerson(person1);
        personService.addPerson(person2);
        personService.deleteAllPersons();
        assertThat(personService.getAllPersons().isEmpty()).isEqualTo(true);
    }

    @Test
    public void exitsById_correctId() {
        personService.addPerson(person1);
        assertThat(personService.existById(person1.getId())).isEqualTo(true);
    }

    @Test
    public void exitsById_nullId() {
        assertThat(personService.existById(null)).isEqualTo(false);
    }

    @Test
    public void exitsById_incorrectValidId() {
        assertThat(personService.existById(ObjectId.get().toHexString())).isEqualTo(false);
    }
    @Test
    public void exitsById_incorrectId() {
        assertThat(personService.existById("belenkoks")).isEqualTo(false);
    }

    @Test
    public void personExistByCombination () {

    }

//    @AfterClass
//    public static void shutdown() {
//        EmbeddedMongoConfig.stop();
//    }
}

