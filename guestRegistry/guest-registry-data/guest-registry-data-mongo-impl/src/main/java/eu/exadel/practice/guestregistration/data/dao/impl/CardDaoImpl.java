package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.dao.CardDao;
import eu.exadel.practice.guestregistration.data.domain.Card;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import eu.exadel.practice.guestregistration.data.entities.CardEntity;

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
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), CardEntity.class);
    }

    @Override
    public Optional<CardEntity> findById(ObjectId id) {
        CardEntity entity = mongoTemplate.findById(id, CardEntity.class);
       //CardDTO
        return Optional.ofNullable (entity);
    }

    @Override
    public List<CardEntity> findAll() {
        return mongoTemplate.findAll(CardEntity.class);
    }

    @Override
    public CardEntity save(CardEntity cardEntity) {
        cardEntity.setId(ObjectId.get());
        return mongoTemplate.save(cardEntity);
    }

    @Override
    public void update(CardEntity cardEntity) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), CardEntity.class)) {
            mongoTemplate.save(cardEntity);
        }
    }

    @Override
    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, CardEntity.class);
    }

    @Override
    public boolean existById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, CardEntity.class);
    }

    @Override
    public boolean exist(Card card) {
        CardEntity cardEntity = card;
        return mongoTemplate.exists(Query.query(Criteria.byExample(cardEntity)), CardEntity.class);
    }
}
