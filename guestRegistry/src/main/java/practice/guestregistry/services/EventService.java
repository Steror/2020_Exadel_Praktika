package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.EventDao;
import practice.guestregistry.models.Event;
import practice.guestregistry.models.Person;

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

    public Optional<Event> getEventById(ObjectId id) {
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

    public void updateEvent(ObjectId id, Event event) {
        if (dao.findById(id).isPresent())
        dao.update(id, event);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteEventById(ObjectId id) {
        if (dao.findById(id).isPresent())
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteAllEvents() { dao.deleteAll(); }
}
