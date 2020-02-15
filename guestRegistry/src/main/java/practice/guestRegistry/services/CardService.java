package practice.guestRegistry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestRegistry.dao.CardDao;
import practice.guestRegistry.models.Card;

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

    public Optional<Card> getCardById (long id) {
        return dao.findById(id);
    }

    public List<Card> getAllCards () {
        return dao.findAll();
    }

    public void addCard (Card newCard) {
        dao.save(newCard);
    }

    public void updateCard (Long id, Card newCard) {
        newCard.setId(id);
        dao.save(newCard);
    }

    public void deleteCardById (Long id) {
        dao.deleteById(id);
    }

}
