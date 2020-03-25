//package practice.guestregistry.services.impl.mockingtest;
//
//import org.bson.types.ObjectId;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import practice.guestregistry.data.api.dao.LocationDao;
//import practice.guestregistry.data.mongo.mappers.LocationMapper;
//import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
//import practice.guestregistry.domain.Location;
//import practice.guestregistry.exceptions.ResourceNotFoundException;
//import practice.guestregistry.services.serviceimpl.LocationServiceImpl;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LocationServiceImplTest {
//    @InjectMocks
//    public LocationMapper mapper;
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
//        location1.setId(new ObjectId().toString());
//        location1.setName("A");
//        location1.setCountry("Lietuva");
//        location1.setCity("Vilnius");
//        location1.setAddress("Zalgirio 90");
//        location1.setLocationType("OFFICE");
//        location1.setPhoneNumber("851212345");
//
//        location2 = new Location();
//        location2.setId(new ObjectId().toString());
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
//        when(locationDao.findById(this.location1.getId())).thenReturn(this.location1);
//        assertEquals(this.location1, locationService.getLocationById(this.location1.getId()));
//    }
//
//    @Test
//    public void getLocationByIdThrowResourceNotFoundException () {
//        when(locationDao.findById(this.location1.getId())).thenReturn(Optional.empty());
//        locationService.getLocationById(this.location1.getId());
//    }
//
//    @Test
//    public void getAllLocations() {
//        List<Location> locationList = new ArrayList<>();
//        locationList.add(this.location1);
//        locationList.add(this.location2);
//
//        when(locationDao.findAll()).thenReturn(locationList);
//
//        assertEquals(locationList, locationService.getAllLocations());
//    }
//
////    @Test
////    public void saveLocation() {
////    }
////
////    @Test
////    public void updateLocation() {
////        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(true);
////        locationService.updateLocation(this.location1);
////    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void updateLocationThrowResourceNotFoundException() {
//        when(locationDao.existById(this.location1.getId())).thenReturn(false);
//        locationService.updateLocation(this.location1);
//    }
//
////    @Test
////    public void deleteLocationById() {
////        Mockito.when(locationDao.existById(this.location1.getId())).thenReturn(true);
////        locationService.deleteLocationById(this.location1.getId());
////    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void deleteLocationByIdThrowResourceNotFoundException() {
//        when(locationDao.existById(this.location1.getId())).thenReturn(false);
////        when(personDao.findById("1")).thenReturn(new Person());
//        locationService.deleteLocationById(this.location1.getId());
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
