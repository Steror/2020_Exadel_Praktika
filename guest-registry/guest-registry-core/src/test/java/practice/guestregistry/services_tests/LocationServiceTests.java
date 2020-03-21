package practice.guestregistry.services_tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureDataMongo //Uses embedded mongo
@ActiveProfiles("test")
public class LocationServiceTests { // Integration testing
    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    Location location1, location2;

    @Before
    public void cleanUp() {
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
        location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");

        location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType("OFFICE");
        location2.setPhoneNumber("851122222");
    }

    @Test
    public void testAddLocation() {
        Location location2 = locationService.saveLocation(location1);
        assertEquals(location1.getCity(), location2.getCity());
        assertEquals(location1.getAddress(), location2.getAddress());
    }

    @Test
    public void testGetAllLocations() {     // Testuoja ir addLocation metoda, tai yra integracinis testas
        locationService.saveLocation(location1);
        locationService.saveLocation(location2);

        List<Location> locList = locationService.getAllLocations();

        for (Location name : locList) { System.out.println(name.toString()); }

        assertEquals(2, locList.size());
    }

    @Test
    public void testGetLocationById() {
        location1 = locationService.saveLocation(location1);

        Optional<Location> location2 = locationService.getLocationById(location1.getId());

        assertEquals(location1, location2.orElse(null));
    }

    @Test
    public void testUpdateLocation() {
        String idToUpdate = locationService.saveLocation(location1).getId();

        location2.setId(idToUpdate);
        locationService.updateLocation(location2);

        assertEquals(location2, locationService.getLocationById(idToUpdate).get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteLocationById() {
        String idToDelete = locationService.saveLocation(location1).getId();
        locationService.saveLocation(location2);

        locationService.deleteLocationById(idToDelete);
        assertEquals(1, locationService.getAllLocations().size());

        locationService.getLocationById(idToDelete);
    }

    @Test
    public void testDeleteAllLocations() {
        locationService.saveLocation(location1);
        locationService.saveLocation(location2);

        locationService.deleteAll();
        assertEquals(0, locationService.getAllLocations().size());
    }
}
