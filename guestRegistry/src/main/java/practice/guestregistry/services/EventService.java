package practice.guestregistry.services;

import eu.exadel.practice.guestregistration.data.dao.EventDao;
import eu.exadel.practice.guestregistration.data.domain.Event;
import eu.exadel.practice.guestregistration.data.domain.Person;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventDao dao;
    private LocationService locationService;
    private PersonService personService;

    @Autowired
    public EventService(EventDao dao, LocationService locationService, PersonService personService) {
        this.dao = dao;
        this.locationService = locationService;
        this.personService = personService;
    }

    public Optional<Event> getEventById(String id) {
        if (dao.findById(id).isPresent())
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public List<Event> getAllEvents() { return dao.findAll(); }

    public Event addEvent(Event event) {
        if (locationService.getLocationById(event.getLocation().getId()).isPresent()) {  // Check if location assigned to event exists
            int presentPersons = 0;
            for (Person person: event.getAttendees()) {
                if (personService.getPersonById(person.getId()).isPresent())    // Check if person with this id exists
                presentPersons++;
                else
                    throw new InvalidParameterException("Invalid field: Person(id: " + person.getId() + " " + person.getLastName() + ") does not exist");
            }
            if (presentPersons == event.getAttendees().size())  // If all persons exist, event is added to DB
                dao.add(event);
        }
        else
            throw new InvalidParameterException("Invalid field: Location(id:" + event.getLocation().getId() + ") assigned to event does not exist");
        return event;
    }

    public void updateEvent(Event event) {
        if (dao.findById(event.getId()).isPresent())
        dao.update(event);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteEventById(String id) {
        if (dao.findById(id).isPresent())
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteAllEvents() { dao.deleteAll(); }
}
