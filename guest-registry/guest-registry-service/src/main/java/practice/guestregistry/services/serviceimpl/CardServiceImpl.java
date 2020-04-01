package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.domain.Card;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.CardService;
import practice.guestregistry.services.service.LocationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final LocationService locationService;
//    private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
    private static final Logger log = LoggerFactory.getILoggerFactory().getLogger("CardServiceImpl");

    @Autowired
    public CardServiceImpl(CardDao cardDao, LocationService locationService) {
        this.cardDao = cardDao;
        this.locationService = locationService;
    }

    @Override
    public Card getCardById (String id) {
        if (id == null) {
            throw new ResourceNotFoundException("id string null");
        }
        return cardDao.findById(id);
    }

    @Override
    public List<Card> getAllCards () {
        return cardDao.findAll();
    }

    @Override
    public void addCard (Card newCard) {
        if (validateCardFields(newCard)) {
           cardDao.add(newCard);
        } else {
            throw new InvalidDocumentStateException("Invalid card information");
        }
    }

    @Override
    public void updateCard (Card newCard) {
        if (cardDao.existById(newCard.getId())) {
            if (validateCardFields(newCard)) {
                cardDao.update(newCard);
            } else {
                throw new InvalidDocumentStateException("Incorrect dates  or location must exist in db");
            }
        } else {
            throw new ResourceNotFoundException("Card by this id doesn't exist");
        }
    }

    @Override
    public void deleteCardById (String id) {
        if (id != null && cardDao.existById(id)) {
            cardDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Card by this id doesn't exist");
        }
    }

    @Override
    public void deleteAllCards () {
        cardDao.deleteAll();
    }

    @Override
    public boolean existById(String id) {
        return cardDao.existById(id);
    }

    @Override
    public boolean existCardContainingIdSerial(String id, String serial) {
        return cardDao.existCardContainingIdSerial(id, serial);
    }

    @Override
    public boolean cardExist(Card card) {
        log.trace("card Exist " + card);
        return cardDao.exist(card);
    }

    //TODO: duplicate NotEmpty? Numbers/Digits?
    private boolean validateCardFields(Card card) {
        log.debug("[validateCardFields] {"+card+"}");

        if (card.getManufactured() == null
                || card.getValidUntil() == null) {
            return false;
        }

        LocalDateTime manufactured;
        LocalDateTime validUntil;
        try {
            manufactured = LocalDateTime.parse(card.getManufactured());
            validUntil = LocalDateTime.parse(card.getValidUntil());
        } catch (DateTimeParseException ex) {
            throw new InvalidDocumentStateException("Incorrect date, failed date parsing");
        }

        log.debug("[validateCardFields] manufactured before parse: " + card.getManufactured()
                + "\n[validateCardFields] manufactured after parse:" + manufactured);
        log.debug("[validateCardFields] validUntil before parse: " + card.getValidUntil()
                + "\n[validateCardFields] validUntil after parse: " + validUntil);

        String locationId = card.getLocationId();
        boolean locationExistInDb = locationService.locationExistById(locationId);
        boolean manufacturedBeforeValid = manufactured.isBefore(validUntil);
        boolean serialNumberExist = cardDao.serialNumberExist(card.getSerialNumber());

        log.debug("[validateCardFields] manufacturedBeforeValid: " + manufacturedBeforeValid);
        log.debug("[validateCardFields] locationExistInDb: " + locationExistInDb);
        log.debug("[validateCardFields] serialNumberExistInDb: " + serialNumberExist);

        return manufacturedBeforeValid && locationExistInDb && !serialNumberExist;
    }
}
