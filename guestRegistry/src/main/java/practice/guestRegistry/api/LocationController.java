package practice.guestRegistry.api;

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
    public void LocationController(LocationService service) { this.service = service; }

    @GetMapping(path="{id}")
    public Optional<Location> getLocation(@PathVariable Long id) {
        return service.getLocationById(id);
    }

    @GetMapping
    public List<Location> getLocations() { return service.getAllLocations(); }

    @PostMapping
    public void addLocation(Location location) { service.addLocation(location); }

    @PutMapping(path="{id}")
    public void updateLocation(@PathVariable Long id, Location location) { service.updateLocation(id, location); }

    @DeleteMapping(path="{id}")
    public void deleteLocation(@PathVariable Long id) { service.deleteLocationById(id); }
}
