package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceImplTest {
    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationDao locationDao;

    Location location1, location2;

    @BeforeEach
    void setUp() {
        location1 = new Location();
        location1.setId(new ObjectId().toString());
        location1.setName("A");
        location1.setCountry("Lietuva");
        location1.setCity("Vilnius");
        location1.setAddress("Zalgirio 90");
        location1.setLocationType("OFFICE");
        location1.setPhoneNumber("851212345");

        location2 = new Location();
        location2.setId(new ObjectId().toString());
        location2.setName("B");
        location2.setCountry("Lietuva");
        location2.setCity("Kaunas");
        location2.setAddress("Geliu 5");
        location2.setLocationType("OFFICE");
        location2.setPhoneNumber("851122222");
    }

    @Test
    void getLocationById() {
        Mockito.when(locationDao.findById(this.location1.getId())).thenReturn(this.location1);

        assertEquals(locationService.getLocationById(this.location1.getId()).get(), this.location1);
    }

    @Test
    void getLocationByIdThrowResourceNotFoundException () {
        Mockito.when(locationDao.findById(this.location1.getId())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> locationService.getLocationById(this.location1.getId())
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(this.location1);
        locationList.add(this.location2);

        Mockito.when(locationDao.findAll()).thenReturn(locationList);

        assertEquals(locationService.getAllLocations(), locationList);
    }

//    @Test
//    void saveLocation() {
//    }
//
//    @Test
//    void updateLocation() {
//        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(true);
//        locationService.updateLocation(this.location1);
//    }

    @Test
    void updateLocationThrowResourceNotFoundException() {
        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> locationService.updateLocation(this.location1)
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

//    @Test
//    void deleteLocationById() {
//        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(true);
//        locationService.deleteLocationById(this.location1.getId());
//    }

    @Test
    void deleteLocationByIdThrowResourceNotFoundException() {
        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> locationService.deleteLocationById(this.location1.getId())
        );

        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

//    @Test
//    void locationExist() {
//    }
//
//    @Test
//    void deleteAll() {
//    }
}
