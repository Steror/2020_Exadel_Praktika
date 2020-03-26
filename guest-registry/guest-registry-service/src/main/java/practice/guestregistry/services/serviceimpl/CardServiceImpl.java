package practice.guestregistry.services.serviceimpl;

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
            throw new InvalidDocumentStateException("Incorrect card details, location must exist in db");
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
        if (cardDao.existById(id)) {
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
    public boolean cardExist(Card card) {
        log.trace("card Exist " + card);
        return cardDao.exist(card);
    }

    //TODO: duplicate NotEmpty? Numbers/Digits?
    private boolean validateCardFields(Card card) {
        if (card.getManufactured() == null
                || card.getValidUntil() == null) {
            log.debug("");
            return false;
        }
        log.debug("[validateCardFields] before parse manufactured: " + card.getManufactured());
        LocalDateTime manufactured = LocalDateTime.parse(card.getManufactured());
        log.debug("[validateCardFields] after parse manufactured: " + manufactured.toString());
        log.debug("[validateCardFields] before parse validUntil: " + card.getValidUntil());
        LocalDateTime validUntil = LocalDateTime.parse(card.getValidUntil());
        log.debug("[validateCardFields] after parse manufactured: " + validUntil.toString());


        boolean manufacturedBeforeValid = manufactured.isBefore(validUntil);
        boolean locationExistInDb = locationService.locationExistById(card.getLocationId());

        log.debug("[validateCardFields] {"+card+"}");
        log.debug("manufacturedBeforeValid: " + manufacturedBeforeValid);
        log.debug("locationExistInDb: " + locationExistInDb);

        return manufacturedBeforeValid && locationExistInDb;
    }
}
