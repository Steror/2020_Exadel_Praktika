package practice.guestregistry.data.api.dao;

import org.bson.types.ObjectId;
import practice.guestregistry.data.api.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<Location> findById (String id);
    List<Location> findAll ();
    Location save (Location location);
    Location update (Location location);
    void deleteById (String id);
    void deleteAll ();
}
