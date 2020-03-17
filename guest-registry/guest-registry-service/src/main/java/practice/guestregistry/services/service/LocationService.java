package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    public void deleteAll ();
    public Optional<Location> getLocationById (String id);
    public List<Location> getAllLocations ();
    public Location saveLocation (Location newLocation);
    public void updateLocation (Location newLocation);
    public void deleteLocationById (String id);
    public boolean locationExist(Location location);
}
