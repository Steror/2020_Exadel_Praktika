package practice.guestregistry.data.api.dao;

import org.bson.types.ObjectId;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.data.api.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Location findById (String id);
    List<Location> findAll ();
    Location save (Location location);
    Location update (Location location);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Location location);
}
