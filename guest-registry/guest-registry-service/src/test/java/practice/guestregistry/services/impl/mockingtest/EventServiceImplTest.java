package practice.guestregistry.services.impl.mockingtest;

import com.sun.java.accessibility.util.EventID;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.mongo.daoimpl.EventDaoImpl;
import practice.guestregistry.data.mongo.daoimpl.LocationDaoImpl;
import practice.guestregistry.domain.Event;
import practice.guestregistry.domain.Location;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.services.service.EventService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.serviceimpl.EventServiceImpl;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest(classes = {
//        EventServiceImpl.class, EventService.class, EventDao.class, EventDaoImpl.class,
//        LocationService.class, LocationServiceImpl.class, LocationDao.class, LocationDaoImpl.class,
//        EventDaoImpl.class, EventDao.class
//})
@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {
    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventDao eventDao;
    @Mock
    private LocationService locationService;
    @Mock
    private PersonService personService;

    Event event1, event2;

    @Before
    public void setUp() {
        Location location = new Location();

        Person person = new Person();

        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person);

        event1 = new Event();
        event1.setName("Praktika");
        event1.setDescription("Java");
        event1.setParticipantsAmount(3);
        event1.setStartDateTime(LocalDateTime.now().withNano(0));
        event1.setEndDateTime(LocalDateTime.now().withNano(0));
        event1.setLocation(location);
        event1.setAttendees(personList);

        event2 = new Event();
        event2.setId(new ObjectId().toString());
        event2.setName("Metine isvyka");
        event2.setDescription("Exadel Vilnius darbuotoju isvyka");
        event2.setParticipantsAmount(50);
        event2.setStartDateTime(LocalDateTime.now());
        event2.setEndDateTime(LocalDateTime.now());
        event2.setLocation(location);
        event2.setAttendees(personList);
    }

    @Test
    public void testGetEventById() {
        Mockito.when(eventDao.existById(event1.getId())).thenReturn(true);
        Mockito.when(eventDao.findById(event1.getId())).thenReturn(event1);

        assertEquals(event1, eventService.getEventById(event1.getId()));
    }

    @Test
    public void testGetEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(event1.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.getEventById(event1.getId())
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    public void testGetAllEvents() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        eventList.add(event2);

        Mockito.when(eventDao.findAll()).thenReturn(eventList);

        assertEquals(eventList, eventService.getAllEvents());
    }

    @Test
    public void testAddEvent() {
        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(true);

        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(true);

        eventService.addEvent(event1);
        verify(eventDao, times(1)).add(event1);
    }

    @Test
    public void testAddEventLocationThrowInvalidDocumentStateException() {
        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(false);

        InvalidDocumentStateException exception = assertThrows(
                InvalidDocumentStateException.class,
                () -> eventService.addEvent(event1)
        );

        assertEquals(InvalidDocumentStateException.class, exception.getClass());
    }

    @Test
    public void testAddEventPersonThrowInvalidDocumentStateException() {
        InvalidDocumentStateException exception = assertThrows(
                InvalidDocumentStateException.class,
                () -> eventService.addEvent(event1)
        );

        assertEquals(InvalidDocumentStateException.class, exception.getClass());
    }

    @Test
    public void testUpdateEvent() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(true);

        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(true);

        eventService.updateEvent(event1);
        verify(eventDao, times(1)).update(event1);
    }

    @Test
    public void testUpdateEventThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.updateEvent(event1)
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    public void testUpdateEventLocationThrowInvalidDocumentStateException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(false);

        InvalidDocumentStateException exception = assertThrows(
                InvalidDocumentStateException.class,
                () -> eventService.updateEvent(event1)
        );

        assertEquals(InvalidDocumentStateException.class, exception.getClass());
    }

    @Test
    public void testUpdateEventPersonThrowInvalidDocumentStateException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(true);

        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(false);

        InvalidDocumentStateException exception = assertThrows(
                InvalidDocumentStateException.class,
                () -> eventService.updateEvent(event1)
        );

        assertEquals(InvalidDocumentStateException.class, exception.getClass());
    }

    @Test
    public void testDeleteEventById() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        eventService.deleteEventById(event1.getId());
        verify(eventDao, times(1)).deleteById(event1.getId());
    }

    @Test
    public void testDeleteEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.deleteEventById(event1.getId())
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    public void testDeleteAllEvents() {
        eventService.deleteAllEvents();
        verify(eventDao, times(1)).deleteAll();
    }

    @Test
    public void testEventExistById() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);
        assertTrue(eventService.eventExistById(event1.getId()));
    }

    @Test
    public void testEventExist() {
        Mockito.when(eventDao.exist(event1))
                .thenReturn(true);
        assertTrue(eventService.eventExist(event1));
    }
}
