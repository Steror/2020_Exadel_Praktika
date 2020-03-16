package eu.exadel.practice.guestregistration.data.dao;

import eu.exadel.practice.guestregistration.data.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventDao {
    Optional<Event> findById (String id);
    List<Event> findAll ();
    void add (Event event);
    void update (String  id, Event event);
    void deleteById (String id);
    void deleteAll ();
}
