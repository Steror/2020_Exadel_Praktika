package practice.guestregistry.services.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.LocationService;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDao dao;

    @Autowired
    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public Location getLocationById(String id) {
        return dao.findById(id);
//        throw new ResourceNotFoundException("Location with this id doesn't exist");
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.findAll();
    }

    @Override
    public void addLocation(Location location) {
        dao.add(location);
    }

    @Override
    public void updateLocation(Location location) {
        if (dao.existById(location.getId())) {
            dao.update(location);
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

    @Override
    public boolean locationExistById(String id) {
        return dao.existById(id);
    }
}
