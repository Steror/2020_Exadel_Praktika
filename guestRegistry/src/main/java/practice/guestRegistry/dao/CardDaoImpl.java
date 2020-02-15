package practice.guestRegistry.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.Card;

import java.util.List;
import java.util.Optional;


@Repository
public class CardDaoImpl implements CardDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public CardDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteAll() {
//        mongoTemplate.remove(Card.class);   <<--- this one doesn't remove Document data
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), Card.class);
    }

    @Override
    public Optional<Card> findById(long id) {
        return Optional.ofNullable (mongoTemplate.findById(id, Card.class));
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(Card.class);
    }

    @Override
    public void save(Card card) {
        mongoTemplate.save(card);
    }

    @Override
    public void deleteById(long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
//        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").is(id)),Card.class);
        mongoTemplate.findAllAndRemove(query, Card.class);
    }
}
