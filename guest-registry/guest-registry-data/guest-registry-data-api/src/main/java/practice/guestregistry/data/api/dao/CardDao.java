package practice.guestregistry.data.api.dao;

import practice.guestregistry.domain.Card;

import java.util.List;
import java.util.Optional;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface CardDao {
    Card findById (String id);
    List<Card> findAll ();
    Card add (Card card);
    Card update (Card card);
    void deleteById (String id);
    void deleteAll ();
    boolean existById (String id);
    boolean exist (Card card);
}




