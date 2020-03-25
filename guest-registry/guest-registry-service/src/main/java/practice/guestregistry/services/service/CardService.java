package practice.guestregistry.services.service;

import practice.guestregistry.domain.Card;

import java.util.List;

public interface CardService {
    Card getCardById (String id);
    List<Card> getAllCards ();
    void addCard (Card card);
    void updateCard (Card card);
    void deleteCardById (String id);
    void deleteAllCards ();
    boolean cardExist(Card card);
    boolean existById(String id);
}
