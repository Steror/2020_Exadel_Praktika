package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    public void deleteAll ();
    public Optional<Card> getCardById (String id);
    public List<Card> getAllCards ();
    public Card addCard (Card card);
    public void updateCard (Card card);
    public void deleteCardById (String id);
    public boolean cardExist(Card card);
    public boolean existById(String id);
}
