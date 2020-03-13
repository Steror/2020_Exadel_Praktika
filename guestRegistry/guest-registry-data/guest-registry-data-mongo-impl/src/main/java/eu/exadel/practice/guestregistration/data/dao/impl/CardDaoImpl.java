package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.dao.CardDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import entities.Card;

import java.util.List;
import java.util.Optional;


@Repository
public class CardDaoImpl implements CardDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public CardDaoImpl(
            MongoTemplate mongoTemplate
    ) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteAll() {
//        mongoTemplate.remove(Card.class);   <<--- this one doesn't remove Document data
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), Card.class);
    }

    @Override
    public Optional<Card> findById(ObjectId id) {
        Card entity = mongoTemplate.findById(id, Card.class);
       CardDTO
        return Optional.ofNullable (entity);
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(Card.class);
    }

    @Override
    public Card save(Card card) {
        card.setId(ObjectId.get());
        return mongoTemplate.save(card);
    }

    @Override
    public void update(Card card) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Card.class)) {
            mongoTemplate.save(card);
        }
    }

    @Override
    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, Card.class);
    }

    @Override
    public boolean existById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, Card.class);
    }

    @Override
    public boolean exist(Card card) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(card)), Card.class);
    }
}
