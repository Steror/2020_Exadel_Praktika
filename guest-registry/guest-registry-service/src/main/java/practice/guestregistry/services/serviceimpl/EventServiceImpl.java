package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.EventService;
import practice.guestregistry.services.service.LocationService;
import practice.guestregistry.services.serviceimpl.PersonServiceImpl;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private EventDao dao;
    private LocationService locationService;
    private PersonServiceImpl personService;

    @Autowired
    public EventServiceImpl(EventDao dao, LocationService locationService, PersonServiceImpl personService) {
        this.dao = dao;
        this.locationService = locationService;
        this.personService = personService;
    }

    public Optional<Event> getEventById(String id) {
        Optional<Event> event = dao.findById(id);
        if (event.isPresent())
            return event;
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
