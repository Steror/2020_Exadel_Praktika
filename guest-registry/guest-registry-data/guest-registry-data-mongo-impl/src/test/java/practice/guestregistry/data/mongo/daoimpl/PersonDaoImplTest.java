package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.mongo.EmbeddedMongoConfig;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

//@SpringBootTest(classes = {PersonDaoImpl.class, PersonDao.class, EmbeddedMongoDb.class})
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {GuestRegistryApiApp.class, PersonDao.class, PersonDaoImpl.class, PersonDomainEntityMapper.class})
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
public class PersonDaoImplTest {

    public PersonDaoImpl personDao;
    @Autowired
    public MongoTemplate mongoTemplate;

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
//    @BeforeClass
//    @AfterClass

    @Before
    public void setUp() {
        personDao = new PersonDaoImpl(mongoTemplate, new PersonDomainEntityMapper());
    }

    @Before
    public void init() {
        personDao.deleteAll();

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

    @Test(expected = EntityCreationException.class)
    public void add_expectException() {
        person1.setId(ObjectId.get().toHexString());
        personDao.add(person1);
    }

    @Test
    public void add_and_findByIdBasic() {
        personDao.add(person1);
        assertThat(personDao.findById(person1.getId())).isEqualTo(person1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_personDoesntExist() {
        personDao.findById(ObjectId.get().toHexString());
    }

    @Test
    public void findAll() {
        personDao.add(person1);
        personDao.add(person2);
        assertThat(personDao.findAll().stream().map(Person::getFirstName))
                .contains(PERSON1_FIRST_NAME, PERSON2_FIRST_NAME);
    }

    @Test
    public void update() {
        personDao.add(person1);
        person2.setId(person1.getId());
        personDao.update(person2);
        assertThat(personDao.findById(person2.getId())).isEqualTo(person2);
    }

    @Test(expected = EntityUpdateException.class)
    public void update_idNull() {
        personDao.add(person1);
        person2.setId(null);
        personDao.update(person2);
    }

    public void deleteById() {
        personDao.add(person1);
        personDao.deleteById(person1.getId());
        assertThat(personDao.findAll().isEmpty()).isEqualTo(true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteById_idDoesntExist() {
        personDao.deleteById(person1.getId());
    }

    @Test
    public void deleteAll() {
        personDao.add(person1);
        personDao.add(person2);
        personDao.deleteAll();
        assertThat(personDao.findAll().isEmpty()).isEqualTo(true);
    }

    @Test
    public void existById() {
        personDao.add(person1);
        assertThat(personDao.existById(person1.getId())).isEqualTo(true);
    }

    @Test
    public void existById_not() {
        assertThat(personDao.existById(person1.getId())).isEqualTo(false);
    }

    @Test
    public void existByFullNameEmailOrAddress_withWhenFullNameMatches() {
        personDao.add(person1);
        person2.setFirstName(PERSON1_FIRST_NAME);
        person2.setMiddleName(PERSON1_MIDDLE_NAME);
        person2.setLastName(PERSON1_LAST_NAME);
        assertThat(personDao.existByFullNameorEmailorAddress(person2)).isEqualTo(true);
    }

    @Test
    public void existByFullNameEmailOrAddress_withWhenFullDoenstMatch() {
        personDao.add(person1);
        person2.setFirstName(PERSON1_FIRST_NAME);
        person2.setLastName(PERSON1_LAST_NAME);
        assertThat(personDao.existByFullNameorEmailorAddress(person2)).isEqualTo(false);
    }
    @Test
    public void existByFullNameEmailOrAddress_withWhenEmailMatches() {
        personDao.add(person1);
        person2.setEmail(PERSON1_EMAIL);
        assertThat(personDao.existByFullNameorEmailorAddress(person2)).isEqualTo(true);
    }

    @Test
    public void existByFullNameEmailOrAddress_withWhenPhoneNumberMatches() {
        personDao.add(person1);
        person2.setPhoneNumber(PERSON1_PHONE_NUMBER);
        assertThat(personDao.existByFullNameorEmailorAddress(person2)).isEqualTo(true);
    }

    public void increaseReferencedCount() {
        personDao.add(person1);
        personDao.increaseReferencedCount(person1.getId());
        assertThat(mongoTemplate.findById(person1.getId(), PersonEntity.class).getReferenced()).isEqualTo(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void increaseReferencedCount_personDoenstExist() {
        personDao.increaseReferencedCount(person1.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void increaseReferencedCount_personDoenstExist_incorrectId() {
        personDao.increaseReferencedCount("asd");
    }

    public void decreaseReferencedCount() {
        personDao.add(person1);
        personDao.decreaseReferencedCount(person1.getId());
        assertThat(mongoTemplate.findById(person1.getId(), PersonEntity.class).getReferenced()).isEqualTo(-1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void decreaseReferencedCount_personDoenstExist() {
        personDao.increaseReferencedCount(person1.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void decreaseReferencedCount_personDoenstExist_incorrectId() {
        personDao.increaseReferencedCount("asd");
    }
}
