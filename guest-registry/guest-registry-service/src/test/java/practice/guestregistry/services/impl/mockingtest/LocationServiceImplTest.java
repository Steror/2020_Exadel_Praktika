//package practice.guestregistry.services.impl.mockingtest;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import practice.guestregistry.data.api.dao.LocationDao;
//import practice.guestregistry.domain.Location;
//import practice.guestregistry.exceptions.ResourceNotFoundException;
//import practice.guestregistry.services.serviceimpl.LocationServiceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class LocationServiceImplTest {
//    @InjectMocks
//    public LocationServiceImpl locationService;
//
//    @Mock
//    public LocationDao locationDao;
//
//    public Location location1, location2;
//
//    @BeforeEach
//    public void setUp() {
//        location1 = new Location();
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType("OFFICE");
//        location1.setPhoneNumber("851212345");
//
//        location2 = new Location();
//        location2.setName("B");
//        location2.setCountry("Lietuva");
//        location2.setCity("Kaunas");
//        location2.setAddress("Geliu 5");
//        location2.setLocationType("OFFICE");
//        location2.setPhoneNumber("851122222");
//    }
//
//    @Test
//    public void getLocationById() {
//        when(locationDao.findById(location1.getId())).thenReturn(location1);
//        assertEquals(location1, locationService.getLocationById(location1.getId()));
//    }
//
//    @Test   //  TODO nuspresti ar servise dar daryti tokius patikrinimus ar perkelti i dao
//    public void getLocationByIdThrowResourceNotFoundException () {
//        when(locationDao.findById(location1.getId())).thenReturn(null);
//
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> locationService.getLocationById(location1.getId())
//        );
//
//        assertEquals(ResourceNotFoundException.class, exception.getClass());
//    }
//
//    @Test
//    public void getAllLocations() {
//        List<Location> locationList = new ArrayList<>();
//        locationList.add(location1);
//        locationList.add(location2);
//
//        when(locationDao.findAll()).thenReturn(locationList);
//
//        assertEquals(locationList, locationService.getAllLocations());
//    }
//
//    @Test
//    public void addLocation() {
//        locationService.addLocation(location1);
//        verify(locationDao, times(1)).add(location1);
//    }
//
//    @Test
//    public void updateLocation() {
//        when(locationDao.existById(location1.getId())).thenReturn(true);
//
//        locationService.updateLocation(location1);
//
//        verify(locationDao, times(1)).update(location1);
//    }
//
//    @Test
//    public void updateLocationThrowResourceNotFoundException() {
//        when(locationDao.existById(location1.getId())).thenReturn(false);
//
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> locationService.updateLocation(location1)
//        );
//
//        assertEquals(ResourceNotFoundException.class, exception.getClass());
//    }
//
//    @Test
//    public void deleteLocationById() {
//        when(locationDao.existById(location1.getId())).thenReturn(true);
//
//        locationService.deleteLocationById(location1.getId());
//
//        verify(locationDao, times(1)).deleteById(location1.getId());
//    }
//
//    @Test
//    public void deleteLocationByIdThrowResourceNotFoundException() {
//        when(locationDao.existById(location1.getId())).thenReturn(false);
//
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> locationService.deleteLocationById(location1.getId())
//        );
//
//        assertEquals(ResourceNotFoundException.class, exception.getClass());
//    }
//
////    @Test
////    public void locationExist() {
////    }
////
////    @Test
////    public void deleteAll() {
////    }
//}
