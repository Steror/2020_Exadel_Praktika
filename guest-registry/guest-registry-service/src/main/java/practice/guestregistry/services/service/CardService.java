package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Card;

import java.util.List;

public interface CardService {
    Card getCardById (String id);
    List<Card> getAllCards ();
    Card addCard (Card card);
    Card updateCard (Card card);
    void deleteCardById (String id);
    void deleteAllCards ();
    boolean cardExist(Card card);
    boolean existById(String id);
}
