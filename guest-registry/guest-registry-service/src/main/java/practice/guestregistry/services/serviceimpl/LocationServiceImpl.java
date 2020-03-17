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
        Location location = dao.findById(id);
        if (location != null) {
            return Optional.of(location);
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    public List<Location> getAllLocations() {
        return dao.findAll();
    }

    public Location saveLocation(Location location) {
        return dao.save(location);
    }

    public void updateLocation(Location location) {
        if (dao.existById(location.getId())) {
            dao.update(location);
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    public void deleteLocationById(String id) {
        if (dao.existById(id)) {
            dao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Location with this id doesn't exist");
        }
    }

    @Override
    public boolean locationExist(Location location) {
        return dao.exist(location);
    }

    //TODO: another existById

    public void deleteAll() {
        dao.deleteAll();
    }

}
