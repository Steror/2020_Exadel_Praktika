package practice.guestregistry.services_tests;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.dao.LocationDaoImpl;
import practice.guestregistry.models.Location;
import practice.guestregistry.services.LocationService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static practice.guestregistry.models.LocationType.OFFICE;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureDataMongo
@ActiveProfiles("test")
public class LocationServiceTests { // Integration testing
    @Autowired
    private LocationService locationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void cleanUp() {
        for (String name : mongoTemplate.getCollectionNames()) {
            mongoTemplate.dropCollection(name);
        }
    }

    @Test
    public void testAddLocation() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        Location location2 = locationService.addLocation(location1);
        assertEquals(location1.getCity(), location2.getCity());
        assertEquals(location1.getAddress(), location2.getAddress());
    }

    @Test
    public void testGetAllLocations() {     // Testuoja ir addLocation metoda, tai yra integracinis testas
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");
        Location location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType(OFFICE);
        location2.setPhoneNumber("851122222");

        locationService.addLocation(location1);
        locationService.addLocation(location2);

        List<Location> locList = locationService.getAllLocations();

        for (Location name : locList) { System.out.println(name.toString()); }

        assertEquals(2, locList.size());
    }

    @Test
    public void testGetLocationById() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        location1 = locationService.addLocation(location1);

        Optional<Location> location2 = locationService.getLocationById(location1.getId());

        assertEquals(location1, location2.orElse(null));
    }

    @Test
    public void testUpdateLocation() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");

        ObjectId idToUpdate = locationService.addLocation(location1).getId();

        Location location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType(OFFICE);
        location2.setPhoneNumber("851122222");

        locationService.updateLocation(idToUpdate, location2);

        location2.setId(idToUpdate);
        assertEquals(location2, locationService.getLocationById(idToUpdate).get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteLocationById() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");
        Location location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType(OFFICE);
        location2.setPhoneNumber("851122222");

        ObjectId idToDelete = locationService.addLocation(location1).getId();
        locationService.addLocation(location2);

        locationService.deleteLocationById(idToDelete);
        assertEquals(1, locationService.getAllLocations().size());

        locationService.getLocationById(idToDelete);
    }

    @Test
    public void testDeleteAllLocations() {
        Location location1 = new Location();
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType(OFFICE);
        location1.setPhoneNumber("851212345");
        Location location2 = new Location();
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType(OFFICE);
        location2.setPhoneNumber("851122222");

        locationService.addLocation(location1);
        locationService.addLocation(location2);

        locationService.deleteAllLocations();
        assertEquals(0, locationService.getAllLocations().size());
    }
}
