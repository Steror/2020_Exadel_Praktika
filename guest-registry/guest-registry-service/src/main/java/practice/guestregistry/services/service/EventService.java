package practice.guestregistry.services.service;

import practice.guestregistry.domain.Event;

import java.util.List;

public interface EventService {
    Event getEventById (String id);
    List<Event> getAllEvents ();
    void addEvent (Event event);
    void updateEvent (Event event);
    void deleteEventById (String id);
    void deleteAllEvents ();
    boolean eventExist (Event event);
    boolean eventExistById (String id);
}
