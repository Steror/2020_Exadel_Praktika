package practice.guestregistry.services.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.domain.Event;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.EventService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.service.PersonService;

import java.util.List;

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
        if (dao.existById(id))
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public List<Event> getAllEvents() { return dao.findAll(); }

    @Override
    public void addEvent(Event event) {
        if (dao.existById(event.getId())) {
            throw new InvalidDocumentStateException("An event with this id already exists");
        }
        else {
            if (locationService.locationExist(event.getLocation())) {  // Check if location assigned to event exists
                int presentPersons = 0;
                for (Person person: event.getAttendees()) {
                    if (personService.personExist(person))    // Check if person exists
                        presentPersons++;
                    else
                        throw new InvalidDocumentStateException("Invalid field: Person(id: " + person.getId()
                                + " " + person.getLastName() + ") does not exist");
                }
                if (presentPersons == event.getAttendees().size())  // If all persons exist, event is added to DB
                    dao.add(event);
            }
            else
                throw new InvalidDocumentStateException("Invalid field: Location(id:"
                        + event.getLocation().getId() + ") assigned to event does not exist");
        }
    }

    @Override
    public void updateEvent(Event event) {
        if (dao.existById(event.getId())) {
            if (locationService.locationExist(event.getLocation())) {  // Check if location assigned to event exists
                int presentPersons = 0;
                for (Person person: event.getAttendees()) {
                    if (personService.personExist(person))    // Check if person with this id exists
                        presentPersons++;
                    else
                        throw new InvalidDocumentStateException("Invalid field: Person(id: " + person.getId()
                                + " " + person.getLastName() + ") does not exist");
                }
                if (presentPersons == event.getAttendees().size())  // If all persons exist, event is added to DB
                    dao.update(event);
                else
                    throw new InvalidDocumentStateException("Some persons do no exist, cannot update event");
            }
            else
                throw new InvalidDocumentStateException("Invalid field: Location(id:"
                        + event.getLocation().getId() + ") assigned to event does not exist");
        }
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public void deleteEventById(String id) {
        if (dao.existById(id))
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Event with this id doesn't exist");
    }

    @Override
    public void deleteAllEvents() {
        dao.deleteAll();
    }

    @Override
    public boolean eventExistById(String id) {
        return dao.existById(id);
    }

    @Override
    public boolean eventExist(Event event) {
        return dao.exist(event);
    }
}
