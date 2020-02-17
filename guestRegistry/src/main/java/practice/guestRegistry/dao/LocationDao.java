package practice.guestRegistry.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import practice.guestRegistry.models.Location;
import practice.guestRegistry.models.Person;
import practice.guestRegistry.models.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Optional<Location> findById (Long id);
    List<Location> findAll ();
    void add (Location location);
    void update (Long id, Location location);
    void deleteById (Long id);
}
