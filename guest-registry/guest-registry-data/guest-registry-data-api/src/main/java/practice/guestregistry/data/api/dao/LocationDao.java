package practice.guestregistry.data.api.dao;

import practice.guestregistry.data.api.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<Location> findById (String id);
    List<Location> findAll ();
    Location add (Location location);
    Location update (Location location);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Location location);
}
