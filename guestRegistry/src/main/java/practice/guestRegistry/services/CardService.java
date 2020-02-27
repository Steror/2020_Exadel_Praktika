package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.CardDao;
import practice.guestregistry.models.Card;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardDao dao;

    @Autowired
    public CardService (CardDao dao) {
        this.dao = dao;
    }

    public void deleteAll () {
        dao.deleteAll();
    }

    public Optional<Card> getCardById (ObjectId id) {
        return dao.findById(id);
    }

    public List<Card> getAllCards () {
        return dao.findAll();
    }

    public Card addCard (Card newCard) {
        dao.save(newCard);
        return newCard;
    }

    public void updateCard (ObjectId id, Card newCard) {
        dao.update(id, newCard);
    }

    public void deleteCardById (ObjectId id) {
        dao.deleteById(id);
    }

}