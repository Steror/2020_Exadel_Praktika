package practice.guestregistry.data.api.dao;


import practice.guestregistry.domain.Location;

import java.util.List;

public interface LocationDao {
    Location findById (String id);
    List<Location> findAll ();
    void add (Location location);
    void update (Location location);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Location location);
}
