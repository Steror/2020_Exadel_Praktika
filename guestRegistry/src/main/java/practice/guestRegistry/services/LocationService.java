package practice.guestRegistry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestRegistry.dao.LocationDao;
import practice.guestRegistry.models.Location;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private LocationDao dao;

    @Autowired
    public LocationService(LocationDao dao) { this.dao = dao; }

    public Optional<Location> getLocationById(Long id) { return dao.findById(id); }

    public List<Location> getAllLocations() { return dao.findAll(); }

    public void addLocation(Location location) { dao.save(location); }

    public void updateLocation(Long id, Location location) {
        location.setId(id);
        dao.save(location);
    }

    public void deleteLocationById(Long id) { dao.deleteById(id); }
}
