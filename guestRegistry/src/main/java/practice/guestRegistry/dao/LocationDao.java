package practice.guestRegistry.dao;

import practice.guestRegistry.models.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    public Optional<Location> findById (Long id);
    public List<Location> findAll ();
    public void save (Location location);
    public void deleteById (Long id);
}
