package practice.guestRegistry.dao;

import practice.guestRegistry.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventDao {
    Optional<Event> findById (Long id);
    List<Event> findAll ();
    void add (Event event);
    void update (Long id, Event event);
    void deleteById (Long id);
    void deleteAll ();
}
