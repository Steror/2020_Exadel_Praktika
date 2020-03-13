package eu.exadel.practice.guestregistration.data.dao;

import org.bson.types.ObjectId;
import entities.Card;

import java.util.List;
import java.util.Optional;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface CardDao {
    void deleteAll ();
    Optional<Card> findById (ObjectId id);
    List<Card> findAll ();
    Card save (Card card);
    void update (Card card);
    void deleteById (ObjectId id);
    boolean existById(ObjectId id);
    boolean exist(Card card);
}




