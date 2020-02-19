package practice.guestRegistry.dao;

import org.bson.types.ObjectId;
import practice.guestRegistry.models.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<Location> findById (ObjectId id);
    List<Location> findAll ();
    void add (Location location);
    void update (ObjectId id, Location location);
    void deleteById (ObjectId id);
    void deleteAll ();
}
