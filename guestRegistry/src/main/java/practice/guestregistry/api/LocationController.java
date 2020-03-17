package practice.guestregistry.api;

import eu.exadel.practice.guestregistration.data.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.services.LocationService;

import javax.validation.Valid;
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
    public Optional<Location> getLocation(@PathVariable String id) {
        return service.getLocationById(id);
    }

    @GetMapping
    public List<Location> getLocations() { return service.getAllLocations(); }

    @PostMapping
    public void addLocation(@Valid @RequestBody Location location) {
        service.addLocation(location); }

    @PutMapping
    public void updateLocation(@Valid @RequestBody Location location) {
        service.updateLocation(location); }

    @DeleteMapping(path="{id}")
    public void deleteLocation(@PathVariable String id) { service.deleteLocationById(id); }
}
