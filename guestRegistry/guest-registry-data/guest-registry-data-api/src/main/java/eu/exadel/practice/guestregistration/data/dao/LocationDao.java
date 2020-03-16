package eu.exadel.practice.guestregistration.data.dao;

import eu.exadel.practice.guestregistration.data.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<Location> findById(String id);

    List<Location> findAll();

    Location add(Location location);

    Location update(Location location);

    void deleteById(String id);

    void deleteAll();
}
