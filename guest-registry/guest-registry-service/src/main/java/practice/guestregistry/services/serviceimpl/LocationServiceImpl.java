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

    private final LocationDao dao;

    @Autowired
    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public Location getLocationById(String id) {
        Optional<Location> location = dao.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.findAll();
    }

    @Override
    public Location addLocation(Location location) {
        return dao.add(location);
    }

    @Override
    public Location updateLocation(Location location) {
        if (dao.existById(location.getId())) {
            return dao.update(location);
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    @Override
    public void deleteLocationById(String id) {
        if (dao.existById(id)) {
            dao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    @Override
    public void deleteAllLocations() {
        dao.deleteAll();
    }

    @Override
    public boolean locationExist(Location location) {
        return dao.exist(location);
    }

    //TODO: another existById
}
