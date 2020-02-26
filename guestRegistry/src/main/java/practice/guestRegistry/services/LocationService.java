package practice.guestRegistry.services;

import org.bson.types.ObjectId;
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

    public Optional<Location> getLocationById(ObjectId id) { return dao.findById(id); }

    public List<Location> getAllLocations() { return dao.findAll(); }

    public void addLocation(Location location) { dao.add(location); }

    public void updateLocation(ObjectId id, Location location) {
        dao.update(id, location);
    }

    public void deleteLocationById(ObjectId id) { dao.deleteById(id); }

    public void deleteAllLocations() { dao.deleteAll(); }
}
