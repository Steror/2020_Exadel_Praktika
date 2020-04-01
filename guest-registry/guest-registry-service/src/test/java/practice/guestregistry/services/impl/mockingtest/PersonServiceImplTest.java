package practice.guestregistry.services.impl.mockingtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.mongodb.core.MongoTemplate;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.serviceimpl.PersonServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @InjectMocks
    public PersonDomainEntityMapper mapper;
    @InjectMocks
    public PersonServiceImpl personService;
    @Mock
    public PersonDao personDao;
    //    public PersonDao personDao = Mockito.mock(PersonDao.class);
    public Person person1;
    public Person person2;

    @Before
    public void init() {
//        MockitoAnnotations.initMocks(this);
//        personDao = Mockito.mock(PersonDaoImpl.class);
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

    @Test
    public void deleteAll() {
    }

    @Test
    public void getPersonById() {
        doReturn(person1).when(personDao).findById(person1.getId());
        assertThat(personService.getPersonById(person1.getId())).isEqualTo(person1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPersonById_whenIdNull() {
        personService.getPersonById(null);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_notExistingId() {
//        doReturn(false).when(personDao).existById(person1.getId());
        personService.updatePerson(person1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteById_personDoesntExist() {
        doReturn(false).when(personDao).existById(person1.getId());
        personService.deletePersonById(person1.getId());
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void add_phoneNumberIncorrect() {
        person1.setPhoneNumber("123a");
        personService.addPerson(person1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void update_phoneNumberIncorrect() {
        person1.setPhoneNumber("123a");
        personService.updatePerson(person1);
    }






    @Test
    public void getAllPersons() {
        List<Person> personList = new ArrayList<>();
        personList.add(this.person1);
        personList.add(this.person2);

        doReturn(personList).when(personDao).findAll();
        assertThat(personService.getAllPersons()).isEqualTo(personList);
    }

    @Test
    public void deletePersonById() {
        List<Person> personList = new ArrayList<>();
        personList.add(this.person1);
        personList.add(this.person2);
        int oldListSize = personList.size();
//        List spy = spy(personList);

        doReturn(Boolean.TRUE).when(personDao).existById(person2.getId());
//        when(personService.deletePersonById(person2.getId())).then(spy.remove(person2)).getMock();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                personList.remove(person2);
                return null;
            }
        }).when(personDao).deleteById(person2.getId());

        personService.deletePersonById(person2.getId());
        assertThat(personList.size() == oldListSize - 1);
    }

    @Test
    public void personExist() {
        List<Person> personList = new ArrayList<>();
        personList.add(this.person1);
        personList.add(this.person2);
        doReturn(Boolean.TRUE).when(personDao).exist(person2);
        assertThat(personService.personExist(person2)).isEqualTo(Boolean.TRUE);
    }


}
