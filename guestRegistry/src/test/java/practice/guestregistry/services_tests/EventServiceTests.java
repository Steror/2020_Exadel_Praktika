package practice.guestregistry.services_tests;

import org.bson.types.ObjectId;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
}
