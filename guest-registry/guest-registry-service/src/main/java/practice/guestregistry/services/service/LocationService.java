package practice.guestregistry.services.service;


import practice.guestregistry.domain.Location;

import java.util.List;

public interface LocationService {
    Location getLocationById (String id);
    List<Location> getAllLocations ();
    void addLocation (Location location);
    void updateLocation (Location location);
    void deleteLocationById (String id);
    void deleteAllLocations ();
    boolean locationExistById (String id);
    boolean locationExist (Location location);
}
