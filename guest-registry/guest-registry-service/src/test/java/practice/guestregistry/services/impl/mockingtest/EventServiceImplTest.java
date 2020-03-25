package practice.guestregistry.services.impl.mockingtest;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.mongo.mappers.EventMapper;
import practice.guestregistry.domain.Event;
import practice.guestregistry.domain.Location;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.serviceimpl.EventServiceImpl;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
class EventServiceImplTest {
    @InjectMocks
    private EventMapper mapper;
    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventDao eventDao;
    @Mock
    private LocationService locationService;
    @Mock
    private PersonService personService;

    Event event1, event2;

    @BeforeEach
    void setUp() {
        Location location = new Location();
        location.setId(new ObjectId().toString());

        Person person = new Person();
        person.setId(new ObjectId().toString());

        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person);

        event1 = new Event();
        event1.setId(new ObjectId().toString());
        event1.setName("Praktika");
        event1.setDescription("Java");
        event1.setParticipantsAmount(3);
        event1.setStartDateTime(LocalDateTime.now());
        event1.setEndDateTime(LocalDateTime.now());
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
    void getEventById() {
        Mockito.when(eventDao.existById(this.event1.getId())).thenReturn(true);
        Mockito.when(eventDao.findById(this.event1.getId())).thenReturn(this.event1);

        assertEquals(this.event1, eventService.getEventById(this.event1.getId()));
    }

    @Test
    void getEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(this.event1.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.getEventById(this.event1.getId())
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    void getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(this.event1);
        eventList.add(this.event2);

        Mockito.when(eventDao.findAll()).thenReturn(eventList);

        assertEquals(eventList, eventService.getAllEvents());
    }

    @Test
    void addEvent() {
        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(true);

        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(true);

        eventService.addEvent(event1);
        verify(eventDao, times(1)).add(event1);
    }

    @Test
    void addEventLocationThrowInvalidParameterException() {
        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(false);

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.addEvent(this.event1)
        );

        assertEquals(InvalidParameterException.class, exception.getClass());
    }

    @Test
    void addEventPersonThrowInvalidParameterException() {
        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(false);

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.addEvent(this.event1)
        );

        assertEquals(InvalidParameterException.class, exception.getClass());
    }

    @Test
    void updateEvent() {
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
    void updateEventThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    void updateEventLocationThrowInvalidParameterException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(false);

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(InvalidParameterException.class, exception.getClass());
    }

    @Test
    void updateEventPersonThrowInvalidParameterException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        Mockito.when(locationService.locationExist(event1.getLocation()))
                .thenReturn(true);

        Mockito.when(personService.personExist(event1.getAttendees().get(0)))
                .thenReturn(false);

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(InvalidParameterException.class, exception.getClass());
    }

    @Test
    void deleteEventById() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(true);

        eventService.deleteEventById(event1.getId());
        verify(eventDao, times(1)).deleteById(event1.getId());
    }

    @Test
    void deleteEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.existById(event1.getId()))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.deleteEventById(event1.getId())
        );

        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

//    @Test
//    void deleteAllEvents() {
//
//    }
}
