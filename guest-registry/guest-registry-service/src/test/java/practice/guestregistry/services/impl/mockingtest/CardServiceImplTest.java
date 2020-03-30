package practice.guestregistry.services.impl.mockingtest;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.serviceimpl.CardServiceImpl;
import practice.guestregistry.services.serviceimpl.LocationServiceImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {
    @InjectMocks
    public CardMapper mapper;
    @InjectMocks
    public CardServiceImpl cardService;

    @Mock
    public CardDao cardDao;
    @Mock
    public LocationServiceImpl locationService;


    //    public PersonDao personDao = Mockito.mock(PersonDao.class);
    public Card card1;
    public Card card2;
    public Location location;

    public static final String CARD1_SERIAL = "card1Serial";
    public static final String CARD1_TYPE = "PERSONNEL";
    public static final String CARD1_MANUFACTURED = LocalDateTime.now().toString();
    public static final String CARD1_VALID_UNTIL = "2023-03-25T22:57:00.795";

    public static final String CARD2_SERIAL = "card1Serial";
    public static final String CARD2_TYPE = "PERSONNEL";
    public static final String CARD2_MANUFACTURED = LocalDateTime.now().toString();
    public static final String CARD2_VALID_UNTIL = "2023-03-25T22:57:00.795";

    public static final String LOCATION1_ID = ObjectId.get().toHexString();
    public static final String LOCATION1_NAME = "Location name";
    public static final String LOCATION1_COUNTRY = "Lietuva";
    public static final String LOCATION1_CITY = "Vilnius";
    public static final String LOCATION1_ADDRESS = "ZALGIRIO 90";
    public static final String LOCATION1_LOCATION_TYPE = "OFFICE";
    public static final String LOCATION1_PHONE_NUMBER = "851212345";

    @Before
    public void init() {
//        MockitoAnnotations.initMocks(this);
//        personDao = Mockito.mock(PersonDaoImpl.class);
        card1 = new Card();
        card1.setId(ObjectId.get().toHexString());
        card1.setSerialNumber(CARD1_SERIAL);
        card1.setManufactured(CARD1_MANUFACTURED);
        card1.setValidUntil(CARD1_VALID_UNTIL);
        card1.setCtype(CARD1_TYPE);
        card1.setLocationId(LOCATION1_ID);
        card1.setLocationName(LOCATION1_NAME);

        card2 = new Card();
        card2.setId(ObjectId.get().toHexString());
        card2.setSerialNumber(CARD2_SERIAL);
        card2.setManufactured(CARD2_MANUFACTURED);
        card2.setValidUntil(CARD2_VALID_UNTIL);
        card2.setCtype(CARD2_TYPE);
        card2.setLocationId(LOCATION1_ID);
        card2.setLocationName(LOCATION1_NAME);

        location = new Location();
        location.setName(LOCATION1_NAME);
        location.setCountry(LOCATION1_COUNTRY);
        location.setCity(LOCATION1_CITY);
        location.setAddress(LOCATION1_ADDRESS);
        location.setLocationType(LOCATION1_LOCATION_TYPE);
        location.setPhoneNumber(LOCATION1_PHONE_NUMBER);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void validateCardFields_manufacturedNull() {
        card1.setManufactured(null);
        cardService.addCard(card1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void validateCardFields_validUntil() {
        card1.setValidUntil(null);
        cardService.addCard(card1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void validateCardFields_manufacturedParse() {
        card1.setManufactured("2012");
        cardService.addCard(card1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void validCardFields_manufacturedAfterValid () {
        card1.setManufactured(CARD1_VALID_UNTIL);
        card1.setValidUntil(CARD1_MANUFACTURED);
        cardService.addCard(card1);
    }

    @Test(expected = InvalidDocumentStateException.class)
    public void validCardFields_locationDoesntExistInDb() {
        doReturn(false).when(locationService).locationExistById(card1.getLocationId());
        cardService.addCard(card1);
    }

    @Test
    public void getCardById() {
        String id = card1.getId();
        doReturn(card1).when(cardDao).findById(id);
        assertThat(cardService.getCardById(id)).isEqualTo(card1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCardById() {
        doReturn(false).when(cardDao).existById(card1.getId());
        cardService.deleteCardById(card1.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateCard() {
//        when(personDao.findById("1")).thenReturn(new Person());
        doReturn(false).when(cardDao).existById(card1.getId());
        cardService.updateCard(card1);
//        assertThat(cardService.getCardById(card1.getId())).isEqualTo(person1);
    }



}
