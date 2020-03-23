package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Event;

import java.util.List;

public interface EventService {
    Event getEventById (String id);
    List<Event> getAllEvents ();
    Event addEvent (Event event);
    Event updateEvent (Event event);
    void deleteEventById (String id);
    void deleteAllEvents ();
}
