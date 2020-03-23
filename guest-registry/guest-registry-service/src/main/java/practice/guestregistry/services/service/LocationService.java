package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Location;

import java.util.List;

public interface LocationService {
    Location getLocationById (String id);
    List<Location> getAllLocations ();
    Location addLocation (Location location);
    Location updateLocation (Location location);
    void deleteLocationById (String id);
    void deleteAllLocations ();
    boolean locationExist(Location location);
}
