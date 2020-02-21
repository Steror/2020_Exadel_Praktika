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
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
    LocationService service;

    @Autowired
    public LocationController(LocationService service) { this.service = service; }

    @GetMapping(path="{id}")
    public Optional<Location> getLocation(@PathVariable ObjectId id) {
        return service.getLocationById(id);
    }

    @GetMapping
    public List<Location> getLocations() { return service.getAllLocations(); }

    @PostMapping
    public void addLocation(@RequestBody Location location) {
        service.addLocation(location); }

    @PutMapping(path="{id}")
    public void updateLocation(@PathVariable ObjectId id, @RequestBody Location location) {
        service.updateLocation(id, location); }

    @DeleteMapping(path="{id}")
    public void deleteLocation(@PathVariable ObjectId id) { service.deleteLocationById(id); }
}
