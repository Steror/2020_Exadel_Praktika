package practice.guestregistry.services.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.LocationService;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private LocationDao dao;

    @Autowired
    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    public Optional<Location> getLocationById(String id) {
        if (dao.findById(id).isPresent())
            return dao.findById(id);
        else
            throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    public List<Location> getAllLocations() {
        return dao.findAll();
    }

    public Location saveLocation(Location location) {
        return dao.save(location);
    }

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

    @Override
    //TODO:
    public boolean locationExist(Location location) {
        return false;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    //TODO:existById
    //exist    <- object resides in db
}
