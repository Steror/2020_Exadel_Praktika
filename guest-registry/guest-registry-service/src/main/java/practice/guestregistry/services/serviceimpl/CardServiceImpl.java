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

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final LocationService locationService;
    private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

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
        }
        throw new InvalidDocumentStateException("Incorrect card details, location must exist in db");
    }

    @Override
    public void updateCard (Card newCard) {
        if (cardDao.existById(newCard.getId())) {
            if (validateCardFields(newCard)) {
                cardDao.update(newCard);
            } else {
                throw new InvalidDocumentStateException("Incorrect card details, location must exist in db");
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
        if (card.getManufactured().isBefore(card.getValidUntil())
                && card.getLocation() != null
                && locationService.locationExistById(card.getLocation().getId())) {
            return true;
        }
        return false;
    }
}
