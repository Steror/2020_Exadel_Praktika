package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.EventDao;
import practice.guestregistry.models.Event;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventDao dao;

    @Autowired
    public EventService(EventDao dao) { this.dao = dao; }

    public Optional<Event> getEventById(ObjectId id) { return dao.findById(id); }

    public List<Event> getAllEvents() { return dao.findAll(); }

    public void addEvent(Event event) { dao.add(event); }

    public void updateEvent(ObjectId id, Event event) {
        dao.update(id, event);
    }

    public void deleteEventById(ObjectId id) { dao.deleteById(id); }

    public void deleteAllEvents() { dao.deleteAll(); }
}
