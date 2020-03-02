package practice.guestregistry.services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import practice.guestregistry.dao.EventDao;
import practice.guestregistry.models.Event;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventDao dao;
    private LocationService locationService;

    @Autowired
    public EventService(EventDao dao, LocationService locationService) {
        this.dao = dao;
        this.locationService = locationService;
    }

    public Optional<Event> getEventById(ObjectId id) {
        if (dao.findById(id).isPresent())
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public List<Event> getAllEvents() { return dao.findAll(); }

    public void addEvent(Event event) {
        if (locationService.getLocationById(event.getLocation().getId()).isPresent())   // If location assigned to event exists
            dao.add(event);
        else
            throw new InvalidParameterException("Invalid field: Location(id:" + event.getLocation().getId() + ") assigned to event does not exist");
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
