package practice.guestRegistry.dao;


import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.Card;

import java.util.List;
import java.util.Optional;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface CardDao {
    public void deleteAll ();
    public Optional<Card> findById (long id);
    public List<Card> findAll ();
    public void save (Card card);
    public void deleteById (long id);
}
