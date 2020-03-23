package practice.guestregistry.services.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.mongodb.core.MongoTemplate;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

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
        MockitoAnnotations.initMocks(this);
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
//        when(personDao.findById("1")).thenReturn(new Person());
        doReturn(person1).when(personDao).findById(person1.getId());
        assertThat(personService.getPersonById(person1.getId()).get()).isEqualTo(person1);
//        BDDMockito.given(this.service.getPersonById(someID))
//                .willReturn(Optional.of(dummyPerson));
//        assertThat(
//                this.service.getPersonById(someID)
//        ).isEqualTo(Optional.of(dummyPerson));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getPersonById_expectException() {
        personService.getPersonById(person1.getId());
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
    public void savePerson() {
        doReturn(person1).when(personDao).save(person1);
        assertThat(personService.savePerson(person1)).isEqualTo(person1);
    }



    @Test(expected = ResourceNotFoundException.class)
    public void updatePerson_expectException() {
        Person updatablePerson = new Person();
        personService.updatePerson(updatablePerson);
    }


    @Test
    public void updatePerson() {
        doReturn(Boolean.TRUE).when(personDao).existById(person2.getId());
        Person updatablePerson = new Person();
        updatablePerson.setId(person2.getId());
        boolean[] saveCalled = {false};
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                saveCalled[0] = true;
                return null;
            }
        }).when(personDao).update(updatablePerson);
        personService.updatePerson(updatablePerson);
        assertThat(saveCalled[0]).isEqualTo(true);
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

    @Test(expected = ResourceNotFoundException.class)
    public void deletePersonById_expectException() {
        personService.deletePersonById(person1.getId());
//        doReturn(Boolean.TRUE).when(personDao).existById(person1.getId());
//        verify(personDao, times(1)).save(person1);
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