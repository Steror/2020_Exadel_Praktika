package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.controllers.dto.mappers.LocationDtoDomainMapper;
import practice.guestregistry.controllers.dto.models.LocationDTO;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.services.service.LocationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "http://localhost:4200")
@Api
public class LocationController {

    LocationService locationService;
    private static final Logger log = LoggerFactory.getLogger(LocationDtoDomainMapper.class);
    private LocationDtoDomainMapper mapper;

    @Autowired
    public LocationController(LocationService locationService, LocationDtoDomainMapper mapper) {
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @GetMapping(path="{id}")
    public Optional<LocationDTO> getLocation(@PathVariable String id) {
        return Optional.ofNullable(mapper.map(locationService.getLocationById(id).get()));
    }

    @GetMapping
    //TODO:trick with streams
    public List<LocationDTO> getLocations() {
        return locationService.getAllLocations().stream().map(mapper::map).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO location) {
        LocationDTO savedLocation = mapper.map(locationService.saveLocation(mapper.map(location)));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedLocation.getId().toString())
                .toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(path="{id}")
//    public ResponseEntity<Location> updateLocation(@PathVariable("id") ObjectId id, @Valid @RequestBody Location newLocation) {
    public ResponseEntity<LocationDTO> updateLocation(@Valid @RequestBody LocationDTO newLocation) {
        locationService.updateLocation(mapper.map(newLocation));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="{id}")
    public void deleteLocation(@PathVariable String id) {
        locationService.deleteLocationById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

}
