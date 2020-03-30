//package practice.guestregistry.services.impl.integrationtest;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import practice.guestregistry.domain.Event;
//import practice.guestregistry.domain.Location;
//import practice.guestregistry.domain.Person;
//import practice.guestregistry.services.service.EventService;
//import practice.guestregistry.services.service.LocationService;
//import practice.guestregistry.services.service.PersonService;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest//(classes = {EventServiceImpl.class, LocationServiceImpl.class, PersonServiceImpl.class,
////        EventDaoImpl.class, LocationDaoImpl.class, PersonDaoImpl.class,
////        EventMapper.class, LocationMapper.class, PersonDomainEntityMapper.class})
//@ExtendWith(SpringExtension.class)
//@AutoConfigureDataMongo
////@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
//@ActiveProfiles("test")
//public class EventServiceTests {
//    @Autowired
//    private EventService eventService;
//
//    @Autowired
//    private LocationService locationService;
//
//    @Autowired
//    private PersonService personService;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    Event event1, event2;
//    Location location1;
//    Person person1, person2;
//
//    final String FIRST_NAME = "Mister";
//
//    @BeforeEach
//    public void setUp() {
//        for (String name : mongoTemplate.getCollectionNames()) {
//            mongoTemplate.dropCollection(name);
//        }
//
//        location1 = new Location();
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType("OFFICE");
//        location1.setPhoneNumber("851212345");
//
//        person1 = new Person();
//        person1.setFirstName("Tomas");
//        person1.setLastName("Kiziela");
//        person2 = new Person();
//        person2.setFirstName(FIRST_NAME);
//        person2.setLastName("T");
//
//        event1 = new Event();
//        event1.setStartDateTime(LocalDateTime.now().withNano(0));
//        event1.setEndDateTime(LocalDateTime.now().withNano(0));
//        event1.setLocation(location1);
//        event1.setAttendees(Collections.singletonList(person1));
//
//        event2 = new Event();
//        event2.setStartDateTime(LocalDateTime.now().withNano(0));
//        event2.setEndDateTime(LocalDateTime.now().withNano(0));
//        event2.setLocation(location1);
//        event2.setAttendees(Collections.singletonList(person2));
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        for (String name : mongoTemplate.getCollectionNames()) {
//            mongoTemplate.dropCollection(name);
//        }
//    }
//
//    @Test
//    public void testAddEvent() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//
//        eventService.addEvent(event1);
//        assertEquals(event1.getLocation(), event1.getLocation());
//    }
//
//    @Test
//    public void testGetEventById() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//
//        eventService.addEvent(event1);
//        Event fetchedEvent = eventService.getEventById(event1.getId());
//
//        assertEquals(event1, fetchedEvent);
//    }
//
//    @Test
//    public void testGetAllEvents() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//        personService.addPerson(person2);
//
//        eventService.addEvent(event1);
//        eventService.addEvent(event2);
//
//        List<Event> list = eventService.getAllEvents();
//        assertEquals(2, list.size());
//    }
//
//    @Test
//    public void testUpdateEvent() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//        personService.addPerson(person2);
//
//        eventService.addEvent(event1);
//        event1.setAttendees(Collections.singletonList(person2));
//
//        eventService.updateEvent(event1);
//        event2 = eventService.getEventById(event1.getId());
//        assertEquals(person2.getFirstName(), event2.getAttendees().get(0).getFirstName());
//    }
//
//    @Test
//    public void testDeleteEventById() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//        personService.addPerson(person2);
//
//        eventService.addEvent(event1);
//        eventService.addEvent(event2);
//        eventService.deleteEventById(event2.getId());
//        assertEquals(1, eventService.getAllEvents().size());
//    }
//
//    @Test
//    public void testDeleteAllEvents() {
//        locationService.addLocation(location1);
//        personService.addPerson(person1);
//        personService.addPerson(person2);
//
//        eventService.addEvent(event1);
//        eventService.addEvent(event2);
//        eventService.deleteAllEvents();
//        assertEquals(0, eventService.getAllEvents().size());
//    }
//}
