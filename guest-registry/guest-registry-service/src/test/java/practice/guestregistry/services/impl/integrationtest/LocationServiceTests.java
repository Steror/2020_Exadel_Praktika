package practice.guestregistry.services.impl.integrationtest;

import com.mongodb.client.MongoClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import practice.guestregistry.GuestRegistryServiceApp;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.EmbeddedMongoConfig;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {GuestRegistryServiceApp.class, EmbeddedMongoConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("db-h2")
public class LocationServiceTests {
    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    Location location1, location2;

    @Before
    public void setUp() throws IOException {

        locationService.deleteAllLocations();

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

    @After
    public void cleanUp() {
        locationService.deleteAllLocations();
    }

    @Test
    public void testAddAndGetLocation() {
        locationService.addLocation(location1);
        Location location = locationService.getLocationById(location1.getId());
        assertEquals(location.getCity(), location1.getCity());
        assertEquals(location.getAddress(), location1.getAddress());
    }

    @Test
    public void testGetAllLocations() {     // Testuoja ir addLocation metoda, tai yra integracinis testas
        locationService.addLocation(location1);
        locationService.addLocation(location2);

        List<Location> locList = locationService.getAllLocations();

        for (Location name : locList) { System.out.println(name.toString()); }

        assertEquals(2, locList.size());
    }

    @Test
    public void testGetLocationById() {
        locationService.addLocation(location1);

        Location location2 = locationService.getLocationById(location1.getId());

        assertEquals(location1, location2);
    }

    @Test
    public void testUpdateLocation() {
        locationService.addLocation(location1);

        location2.setId(location1.getId());
        locationService.updateLocation(location2);

        assertAll(
                () -> assertEquals(location2, locationService.getLocationById(location1.getId())),
                () -> assertEquals(1, locationService.getAllLocations().size())
        );
    }

    @Test
    public void testDeleteLocationById() {
        locationService.addLocation(location1);
        locationService.addLocation(location2);

        locationService.deleteLocationById(location1.getId());
        assertEquals(1, locationService.getAllLocations().size());
    }

    @Test
    public void testDeleteLocationByIdThrowsResourceNotFoundException() {
        locationService.addLocation(location2);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> locationService.deleteLocationById(location1.getId())
        );

        Assertions.assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    public void testDeleteAllLocations() {
        locationService.addLocation(location1);
        locationService.addLocation(location2);

        locationService.deleteAllLocations();
        assertEquals(0, locationService.getAllLocations().size());
    }

//    @AfterClass
//    public static void shutdown() {
//        EmbeddedMongoConfig.stop();
//    }
//
}
