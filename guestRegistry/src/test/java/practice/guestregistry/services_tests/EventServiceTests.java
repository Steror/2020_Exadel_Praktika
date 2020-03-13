package practice.guestregistry.services_tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.models.Event;
import practice.guestregistry.models.Location;
import practice.guestregistry.models.Person;
import practice.guestregistry.services.EventService;
import practice.guestregistry.services.LocationService;
import practice.guestregistry.services.PersonService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static practice.guestregistry.models.LocationType.OFFICE;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureDataMongo
@ActiveProfiles("test")
public class EventServiceTests {
    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void cleanUp() {
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
    }

    @Test
    public void testAddEvent() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person = new Person();
        person.setFirstName("Tomas");
        person.setLastName("Kiziela");

        location1 = locationService.addLocation(location1);
        person = personService.addPerson(person);

        Event event = new Event();
        event.setStart_date_time(LocalDateTime.now());
        event.setEnd_date_time(LocalDateTime.now());
        event.setLocation(location1);
        event.setAttendees(Collections.singletonList(person));

        Event createdEvent = eventService.addEvent(event);
        assertEquals(createdEvent.getLocation(), event.getLocation());
    }

    @Test
    public void testGetEventById() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person = new Person();
        person.setFirstName("Tomas");
        person.setLastName("Kiziela");

        location1 = locationService.addLocation(location1);
        person = personService.addPerson(person);

        Event event = new Event();
        event.setStart_date_time(LocalDateTime.now());
        event.setEnd_date_time(LocalDateTime.now());
        event.setLocation(location1);
        event.setAttendees(Collections.singletonList(person));

        Event createdEvent = eventService.addEvent(event);
        Event fetchedEvent = eventService.getEventById(createdEvent.getId()).get();

        assertEquals(createdEvent, fetchedEvent);
    }

    @Test
    public void testGetAllEvents() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person = new Person();
        person.setFirstName("Tomas");
        person.setLastName("Kiziela");

        location1 = locationService.addLocation(location1);
        person = personService.addPerson(person);

        Event event1 = new Event();
        event1.setStart_date_time(LocalDateTime.now());
        event1.setEnd_date_time(LocalDateTime.now());
        event1.setLocation(location1);
        event1.setAttendees(Collections.singletonList(person));
        Event event2 = new Event();
        event2.setStart_date_time(LocalDateTime.now());
        event2.setEnd_date_time(LocalDateTime.now());
        event2.setLocation(location1);
        event2.setAttendees(Collections.singletonList(person));

        eventService.addEvent(event1);
        eventService.addEvent(event2);

        List<Event> list = eventService.getAllEvents();
        assertEquals(2, list.size());
    }

    @Test
    public void testUpdateEvent() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person1 = new Person();
        person1.setFirstName("Tomas");
        person1.setLastName("Kiziela");
        Person person2 = new Person();
        person2.setFirstName("Mister");
        person2.setLastName("T");

        location1 = locationService.addLocation(location1);
        person1 = personService.addPerson(person1);
        person2 = personService.addPerson(person2);

        Event event1 = new Event();
        event1.setStart_date_time(LocalDateTime.now());
        event1.setEnd_date_time(LocalDateTime.now());
        event1.setLocation(location1);
        event1.setAttendees(Collections.singletonList(person1));

        Event event2 = eventService.addEvent(event1);
        event2.setAttendees(Collections.singletonList(person2));

        eventService.updateEvent(event2.getId(), event2);
        event2 = eventService.getEventById(event2.getId()).get();
        assertEquals("Mister", event2.getAttendees().get(0).getFirstName());
    }

    @Test
    public void testDeleteEventById() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person1 = new Person();
        person1.setFirstName("Tomas");
        person1.setLastName("Kiziela");
        Person person2 = new Person();
        person2.setFirstName("Mister");
        person2.setLastName("T");

        location1 = locationService.addLocation(location1);
        person1 = personService.addPerson(person1);
        person2 = personService.addPerson(person2);

        Event event1 = new Event();
        event1.setStart_date_time(LocalDateTime.now());
        event1.setEnd_date_time(LocalDateTime.now());
        event1.setLocation(location1);
        event1.setAttendees(Collections.singletonList(person1));
        Event event2 = new Event();
        event2.setStart_date_time(LocalDateTime.now());
        event2.setEnd_date_time(LocalDateTime.now());
        event2.setLocation(location1);
        event2.setAttendees(Collections.singletonList(person2));

        eventService.addEvent(event1);
        event2 = eventService.addEvent(event2);
        eventService.deleteEventById(event2.getId());
        assertEquals(1, eventService.getAllEvents().size());
    }

    @Test
    public void testDeleteAllEvents() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Person person1 = new Person();
        person1.setFirstName("Tomas");
        person1.setLastName("Kiziela");
        Person person2 = new Person();
        person2.setFirstName("Mister");
        person2.setLastName("T");

        location1 = locationService.addLocation(location1);
        person1 = personService.addPerson(person1);
        person2 = personService.addPerson(person2);

        Event event1 = new Event();
        event1.setStart_date_time(LocalDateTime.now());
        event1.setEnd_date_time(LocalDateTime.now());
        event1.setLocation(location1);
        event1.setAttendees(Collections.singletonList(person1));
        Event event2 = new Event();
        event2.setStart_date_time(LocalDateTime.now());
        event2.setEnd_date_time(LocalDateTime.now());
        event2.setLocation(location1);
        event2.setAttendees(Collections.singletonList(person2));

        eventService.addEvent(event1);
        eventService.addEvent(event2);
        eventService.deleteAllEvents();
        assertEquals(0, eventService.getAllEvents().size());
    }

}
