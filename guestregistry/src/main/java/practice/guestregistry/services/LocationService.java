package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.LocationDao;
import practice.guestregistry.models.Location;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private LocationDao dao;

    @Autowired
    public LocationService(LocationDao dao) { this.dao = dao; }

    public Optional<Location> getLocationById(ObjectId id) {
        if (dao.findById(id).isPresent())
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public List<Location> getAllLocations() { return dao.findAll(); }

    public Location addLocation(Location location) { return dao.add(location); }

    public void updateLocation(ObjectId id, Location location) {
        if (dao.findById(id).isPresent())
            dao.update(id, location);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteLocationById(ObjectId id) {
        if (dao.findById(id).isPresent())
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteAllLocations() { dao.deleteAll(); }
}
