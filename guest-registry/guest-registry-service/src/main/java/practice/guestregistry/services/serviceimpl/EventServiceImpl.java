package practice.guestregistry.services.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.EventService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventDao dao;
    private final LocationService locationService;
    private final PersonService personService;

    @Autowired
    public EventServiceImpl(EventDao dao, LocationService locationService, PersonService personService) {
        this.dao = dao;
        this.locationService = locationService;
        this.personService = personService;
    }

    @Override
    public Event getEventById(String id) {
        Optional<Event> event = dao.findById(id);
        if (event.isPresent())
            return event.get();
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public List<Event> getAllEvents() { return dao.findAll(); }

    @Override
    public Event addEvent(Event event) {
        if (locationService.locationExist(event.getLocation())) {  // Check if location assigned to event exists
            int presentPersons = 0;
            for (Person person: event.getAttendees()) {
                if (personService.personExist(person))    // Check if person exists
                presentPersons++;
                else
                    throw new InvalidParameterException("Invalid field: Person(id: " + person.getId()
                            + " " + person.getLastName() + ") does not exist");
            }
            if (presentPersons == event.getAttendees().size())  // If all persons exist, event is added to DB
                dao.add(event);
        }
        else
            throw new InvalidParameterException("Invalid field: Location(id:"
                    + event.getLocation().getId() + ") assigned to event does not exist");
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        if (dao.findById(event.getId()).isPresent()) {
            if (locationService.locationExist(event.getLocation())) {  // Check if location assigned to event exists
                int presentPersons = 0;
                for (Person person: event.getAttendees()) {
                    if (personService.personExist(person))    // Check if person with this id exists
                        presentPersons++;
                    else
                        throw new InvalidParameterException("Invalid field: Person(id: " + person.getId()
                                + " " + person.getLastName() + ") does not exist");
                }
                if (presentPersons == event.getAttendees().size())  // If all persons exist, event is added to DB
                    return dao.update(event);
                else
                    throw new InvalidParameterException("Some persons do no exist, cannot update event");
            }
            else
                throw new InvalidParameterException("Invalid field: Location(id:"
                        + event.getLocation().getId() + ") assigned to event does not exist");
        }
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public void deleteEventById(String id) {
        if (dao.findById(id).isPresent())
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public void deleteAllEvents() { dao.deleteAll(); }
}
