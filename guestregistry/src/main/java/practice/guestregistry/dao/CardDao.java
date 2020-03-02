package practice.guestregistry.dao;


import org.bson.types.ObjectId;
import practice.guestregistry.models.Card;

import java.util.List;
import java.util.Optional;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface CardDao {
    public void deleteAll ();
    public Optional<Card> findById (ObjectId id);
    public List<Card> findAll ();
    public void save (Card card);
    public void update (ObjectId id, Card card);
    public void deleteById (ObjectId id);
}




