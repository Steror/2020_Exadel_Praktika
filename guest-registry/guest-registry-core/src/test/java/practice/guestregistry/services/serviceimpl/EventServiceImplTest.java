package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EventServiceImplTest {
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
        event1.setParticipants_amount(3);
        event1.setStart_date_time(LocalDateTime.now());
        event1.setEnd_date_time(LocalDateTime.now());
        event1.setLocation(location);
        event1.setAttendees(personList);

        event2 = new Event();
        event2.setId(new ObjectId().toString());
        event2.setName("Metine isvyka");
        event2.setDescription("Exadel Vilnius darbuotoju isvyka");
        event2.setParticipants_amount(50);
        event2.setStart_date_time(LocalDateTime.now());
        event2.setEnd_date_time(LocalDateTime.now());
        event2.setLocation(location);
        event2.setAttendees(personList);
    }

    @Test
    void getEventById() {
        Mockito.when(eventDao.findById(this.event1.getId())).thenReturn(java.util.Optional.ofNullable(this.event1));

        assertEquals(eventService.getEventById(this.event1.getId()).get(), this.event1);
    }

    @Test
    void getEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.findById(this.event1.getId())).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.getEventById(this.event1.getId())
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(this.event1);
        eventList.add(this.event2);

        Mockito.when(eventDao.findAll()).thenReturn(eventList);

        assertEquals(eventService.getAllEvents(), eventList);
    }

    @Test
    void addEvent() {
        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getLocation()));

        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getAttendees().get(0)));

        assertEquals(eventService.addEvent(event1), event1);
    }

    @Test
    void addEventLocationThrowInvalidParameterException() {
        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.empty());

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.addEvent(this.event1)
        );

        assertEquals(exception.getClass(), InvalidParameterException.class);
    }

    @Test
    void addEventPersonThrowInvalidParameterException() {
        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.empty());

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.addEvent(this.event1)
        );

        assertEquals(exception.getClass(), InvalidParameterException.class);
    }

    @Test
    void updateEvent() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.ofNullable(event1));

        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getLocation()));

        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getAttendees().get(0)));

        eventService.updateEvent(event1);
        verify(eventDao, times(1)).update(event1);
    }

    @Test
    void updateEventThrowResourceNotFoundException() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.empty());

        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getLocation()));

        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getAttendees().get(0)));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void updateEventLocationThrowInvalidParameterException() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.ofNullable(event1));

        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.empty());

        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getAttendees().get(0)));

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(exception.getClass(), InvalidParameterException.class);
    }

    @Test
    void updateEventPersonThrowInvalidParameterException() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.ofNullable(event1));

        Mockito.when(locationService.getLocationById(event1.getLocation().getId()))
                .thenReturn(java.util.Optional.ofNullable(event1.getLocation()));

        Mockito.when(personService.getPersonById(event1.getAttendees().get(0).getId()))
                .thenReturn(java.util.Optional.empty());

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> eventService.updateEvent(this.event1)
        );

        assertEquals(exception.getClass(), InvalidParameterException.class);
    }

    @Test
    void deleteEventById() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.ofNullable(event1));

        eventService.deleteEventById(event1.getId());
        verify(eventDao, times(1)).deleteById(event1.getId());
    }

    @Test
    void deleteEventByIdThrowResourceNotFoundException() {
        Mockito.when(eventDao.findById(event1.getId()))
                .thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> eventService.deleteEventById(event1.getId())
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

//    @Test
//    void deleteAllEvents() {
//
//    }
}
