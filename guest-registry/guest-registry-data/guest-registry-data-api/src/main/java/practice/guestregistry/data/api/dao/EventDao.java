package practice.guestregistry.data.api.dao;

import practice.guestregistry.domain.Event;

import java.util.List;

public interface EventDao {
    Event findById (String id);
    List<Event> findAll ();
    void add (Event event);
    void update (Event event);
    void deleteById (String id);
    void deleteAll ();
    boolean existById (String id);
    boolean exist (Event event);
}
