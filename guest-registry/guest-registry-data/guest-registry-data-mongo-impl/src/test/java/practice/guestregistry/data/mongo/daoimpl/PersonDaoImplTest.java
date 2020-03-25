package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.mongo.EmbeddedMongoConfig;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

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

//    @BeforeClass
//    @AfterClass

    @Before
    public void setUp() {
        personDao = new PersonDaoImpl(mongoTemplate, new PersonDomainEntityMapper());
    }

    @Before
    public void init() {
        person1 = new Person();
        person1.setId(ObjectId.get().toHexString());
        person1.setFirstName("first");
        person1.setLastName("surname");
        person1.setEmail("email@email.com");
        person1.setPhoneNumber("12345");

        person2 = new Person();
        person2.setId(ObjectId.get().toHexString());
        person2.setFirstName("first");
        person2.setLastName("surname");
        person2.setEmail("email@email.com");
        person2.setPhoneNumber("67890");
    }

    @After
    public void empty() {
        mongoTemplate.getCollectionNames().forEach(name -> mongoTemplate.dropCollection(name));
    }

    @Test
    public void deleteAll() {
        mongoTemplate.getCollectionNames().forEach((val) -> System.out.println(val));
    }

    @Test
    public void getPersonById() {
//        mongoTemplate.findAll(PersonEntity.class);
//        Person savedPerson = mongoTemplate.save(person1);

        //buvo geras
//        Person savedPerson = personDao.add(person1);
//        System.out.println(savedPerson);
//        System.out.println("\n!!!!!!!!\n");
//        mongoTemplate.findAll(Person.class).stream().forEach(System.out::println);
//        System.out.println("\n!!!!!!!!\n");
//        assertThat(personDao.findById(savedPerson.getId())).isEqualTo(savedPerson);
//        System.out.println(personDao.findById(new ObjectId().toHexString()));
    }

//    @Test(expected = ResourceNotFoundException.class)
//    public void getPersonById_expectException() {
//        personService.getPersonById(person1.getId());
//    }
//
//    @Test
//    public void getAllPersons() {
//        List<Person> personList = new ArrayList<>();
//        personList.add(this.person1);
//        personList.add(this.person2);
//
//        doReturn(personList).when(personDao).findAll();
//        assertThat(personService.getAllPersons()).isEqualTo(personList);
//    }
//
//    @Test
//    public void savePerson() {
//        doReturn(person1).when(personDao).save(person1);
//        assertThat(personService.savePerson(person1)).isEqualTo(person1);
//    }
//
//
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void updatePerson_expectException() {
//        Person updatablePerson = new Person();
//        personService.updatePerson(updatablePerson);
//    }
//
//
//    @Test
//    public void updatePerson() {
//        doReturn(Boolean.TRUE).when(personDao).existById(person2.getId());
//        Person updatablePerson = new Person();
//        updatablePerson.setId(person2.getId());
//        boolean[] saveCalled = {false};
//        doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
//                saveCalled[0] = true;
//                return null;
//            }
//        }).when(personDao).update(updatablePerson);
//        personService.updatePerson(updatablePerson);
//        assertThat(saveCalled[0]).isEqualTo(true);
//    }
//
//
//    @Test
//    public void deletePersonById() {
//        List<Person> personList = new ArrayList<>();
//        personList.add(this.person1);
//        personList.add(this.person2);
//        int oldListSize = personList.size();
////        List spy = spy(personList);
//
//        doReturn(Boolean.TRUE).when(personDao).existById(person2.getId());
////        when(personService.deletePersonById(person2.getId())).then(spy.remove(person2)).getMock();
//        doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
//                personList.remove(person2);
//                return null;
//            }
//        }).when(personDao).deleteById(person2.getId());
//
//        personService.deletePersonById(person2.getId());
//        assertThat(personList.size() == oldListSize - 1);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void deletePersonById_expectException() {
//        personService.deletePersonById(person1.getId());
////        doReturn(Boolean.TRUE).when(personDao).existById(person1.getId());
////        verify(personDao, times(1)).save(person1);
//    }
//
//    @Test
//    public void personExist() {
//        List<Person> personList = new ArrayList<>();
//        personList.add(this.person1);
//        personList.add(this.person2);
//        doReturn(Boolean.TRUE).when(personDao).exist(person2);
//        assertThat(personService.personExist(person2)).isEqualTo(Boolean.TRUE);
//    }
//

}
