package practice.guestregistry.services_tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import practice.guestregistry.dao.LocationDaoImpl;
import practice.guestregistry.models.Location;
import practice.guestregistry.services.LocationService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static practice.guestregistry.models.LocationType.OFFICE;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationServiceTests2 {
    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationDaoImpl dao;

    @Test
    public void testGetAllLocations() {
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

        List<Location> locationList = new ArrayList<>();
        locationList.add(location1);
        locationList.add(location2);

        Mockito.when(dao.findAll()).thenReturn(locationList);

        assertThat(locationService.getAllLocations()).isEqualTo(locationList);
    }

}
