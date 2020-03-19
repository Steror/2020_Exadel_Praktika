package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    public void deleteAllEvents ();
    public Optional<Event> getEventById (String id);
    public List<Event> getAllEvents ();
    public Event addEvent (Event event);
    public void updateEvent (Event event);
    public void deleteEventById (String id);
}
