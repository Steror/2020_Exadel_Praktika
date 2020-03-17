package practice.guestregistry.services;

import eu.exadel.practice.guestregistration.data.dao.LocationDao;
import eu.exadel.practice.guestregistration.data.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private LocationDao dao;

    @Autowired
    public LocationService(LocationDao dao) { this.dao = dao; }

    public Optional<Location> getLocationById(String id) {
        if (dao.findById(id).isPresent())
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public List<Location> getAllLocations() { return dao.findAll(); }

    public Location addLocation(Location location) { return dao.add(location); }

    public void updateLocation(Location location) {
        if (dao.findById(location.getId()).isPresent())
            dao.update(location);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteLocationById(String id) {
        if (dao.findById(id).isPresent())
            dao.deleteById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public void deleteAllLocations() { dao.deleteAll(); }
}
