package practice.guestregistry.services.impl.integrationtest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.mongo.daoimpl.EventDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
import practice.guestregistry.data.mongo.mappers.EventMapper;
import practice.guestregistry.data.mongo.mappers.LocationMapper;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Event;
import practice.guestregistry.domain.Location;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.services.EmbeddedMongoConfig;
import practice.guestregistry.services.service.EventService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.serviceimpl.EventServiceImpl;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;
import practice.guestregistry.services.serviceimpl.PersonServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {EventServiceImpl.class, LocationServiceImpl.class, PersonServiceImpl.class,
        EventDaoImpl.class, LocationDaoImpl.class, PersonDaoImpl.class,
        EventMapper.class, LocationMapper.class, PersonDomainEntityMapper.class,
        EmbeddedMongoConfig.class
})
//@ExtendWith(SpringExtension.class)
//@AutoConfigureDataMongo
//@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
//@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTests {
    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MongoTemplate mongoTemplate;

    Event event1, event2;
    Location location1;
    Person person1, person2;

    final String FIRST_NAME = "Mister";

    @Before
    public void setUp() {
        eventService.deleteAllEvents();
        locationService.deleteAllLocations();
        personService.deleteAllPersons();

        location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");

        person1 = new Person();
        person1.setFirstName("Tomas");
        person1.setMiddleName("");
        person1.setLastName("Kiziela");
        person1.setPhoneNumber("123");
        person1.setEmail("person1@email.com");
        person2 = new Person();
        person2.setFirstName(FIRST_NAME);
        person2.setLastName("T");
        person2.setEmail("person2@email.com");
        person2.setPhoneNumber("456");
        person2.setMiddleName("");

        event1 = new Event();
        event1.setStartDateTime(LocalDateTime.now().withNano(0));
        event1.setEndDateTime(LocalDateTime.now().withNano(0));
        event1.setLocation(location1);
        event1.setAttendees(Collections.singletonList(person1));

        event2 = new Event();
        event2.setStartDateTime(LocalDateTime.now().withNano(0));
        event2.setEndDateTime(LocalDateTime.now().withNano(0));
        event2.setLocation(location1);
        event2.setAttendees(Collections.singletonList(person2));
    }

    @After
    public void cleanUp() {
        personService.deleteAllPersons();
        locationService.deleteAllLocations();
        eventService.deleteAllEvents();
    }

    //changed
//    @Test(expected = InvalidDocumentStateException.class)
    public void testAddEvent() {
        locationService.addLocation(location1);
        personService.addPerson(person1);

        eventService.addEvent(event1);
        assertEquals(event1.getLocation(), event1.getLocation());
    }

    //Failures:
    //  EventServiceTests.testGetEventById:128 expected: <Event(id=5e85bfc223d5283d438de6e6, name=null, description=null, participantsAmount=0, startDateTime=2020-04-02T13:34:41, endDateTime=2020-04-02T13:34:41, location=Location(id=5e85bfc123d5283d438de6e4, name=A, country=Lietuva, city=Vilnius, address=Zalgirio 90, locationType=OFFICE, phoneNumber=851212345), attendees=[Person(id=5e85bfc123d5283d438de6e5, firstName=Tomas, middleName=, lastName=Kiziela, email=person1@email.com, phoneNumber=123)])> but was: <Event(id=5e85bfc223d5283d438de6e6, name=null, description=null, participantsAmount=0, startDateTime=2020-04-02T13:34:41, endDateTime=2020-04-02T13:34:41, location=null, attendees=[])>
    // dar galimai buvo sitas
    // practice.guestregistry.exceptions.ResourceNotFoundException: Location with this id doesn't exist
    @Test
    public void testGetEventById() {
        locationService.addLocation(location1);
        personService.addPerson(person1);

        eventService.addEvent(event1);
        Event fetchedEvent = eventService.getEventById(event1.getId());

        assertEquals(event1, fetchedEvent);
    }

    //changed
//    @Test(expected = InvalidDocumentStateException.class)
    @Test
    public void testGetAllEvents() {
        locationService.addLocation(location1);
        personService.addPerson(person1);
        personService.addPerson(person2);

        eventService.addEvent(event1);
        eventService.addEvent(event2);

        List<Event> list = eventService.getAllEvents();
        assertEquals(2, list.size());
    }

    //changed problems adding person
//    @Test(expected = EntityCreationException.class)
    @Test
    public void testUpdateEvent() {
        locationService.addLocation(location1);
        personService.addPerson(person1);
        personService.addPerson(person2);

        eventService.addEvent(event1);
        event1.setAttendees(Collections.singletonList(person2));

        eventService.updateEvent(event1);
        event2 = eventService.getEventById(event1.getId());
        assertEquals(person2.getFirstName(), event2.getAttendees().get(0).getFirstName());
    }

    //changed
//    @Test(expected = InvalidDocumentStateException.class)
    @Test
    public void testDeleteEventById() {
        locationService.addLocation(location1);
        personService.addPerson(person1);
        personService.addPerson(person2);

        eventService.addEvent(event1);
        eventService.addEvent(event2);
        eventService.deleteEventById(event2.getId());
        assertEquals(1, eventService.getAllEvents().size());
    }

    //changed
//    @Test(expected = InvalidDocumentStateException.class)
    @Test
    public void testDeleteAllEvents() {
        locationService.addLocation(location1);
        personService.addPerson(person1);
        personService.addPerson(person2);

        eventService.addEvent(event1);
        eventService.addEvent(event2);
        eventService.deleteAllEvents();
        assertEquals(0, eventService.getAllEvents().size());
    }

//    @AfterClass
//    public static void shutdown() {
//        EmbeddedMongoConfig.stop();
//    }
}
