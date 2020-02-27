package practice.guestregistry.dao;

import org.bson.types.ObjectId;
import practice.guestregistry.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventDao {
    Optional<Event> findById (ObjectId id);
    List<Event> findAll ();
    void add (Event event);
    void update (ObjectId id, Event event);
    void deleteById (ObjectId id);
    void deleteAll ();
}
