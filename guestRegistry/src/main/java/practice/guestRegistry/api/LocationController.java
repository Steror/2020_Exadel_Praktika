package practice.guestRegistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestRegistry.models.Location;
import practice.guestRegistry.services.LocationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    LocationService service;

    @Autowired
    public LocationController(LocationService service) { this.service = service; }

    @GetMapping(path="{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Optional<Location> getLocation(@PathVariable ObjectId id) {
        return service.getLocationById(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Location> getLocations() { return service.getAllLocations(); }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public void addLocation(Location location) {
        service.addLocation(location); }

    @PutMapping(path="{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void updateLocation(@PathVariable ObjectId id, Location location) { service.updateLocation(id, location); }

    @DeleteMapping(path="{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteLocation(@PathVariable ObjectId id) { service.deleteLocationById(id); }
}
