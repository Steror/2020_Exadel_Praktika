package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.CardDao;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.models.Card;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardDao cardDao;

    @Autowired
    public CardService (CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public void deleteAll () {
        cardDao.deleteAll();
    }

    public Optional<Card> getCardById (ObjectId id) {
        System.out.println(!cardDao.existById(id));
        if (!cardDao.existById(id)) {
            throw new ResourceNotFoundException("Card by this id doesn't exist");
        }
        return cardDao.findById(id);
    }

    public List<Card> getAllCards () {
        return cardDao.findAll();
    }

    public Card addCard (Card newCard) {
//        if (newCard.getSerialNumber().isEmpty()) {
//            throw new SomeException("invalid serial");
//        }
        return cardDao.save(newCard);
    }

    public void updateCard (ObjectId id, Card newCard) {
        if (cardDao.existById(id)) {
            cardDao.update(id, newCard);
        } else {
            throw new ResourceNotFoundException("Card by this id doesn't exist");
        }
    }

    public void deleteCardById (ObjectId id) {
        if (cardDao.existById(id)) {
            cardDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Card by this id doesn't exist");
        }
    }



}
