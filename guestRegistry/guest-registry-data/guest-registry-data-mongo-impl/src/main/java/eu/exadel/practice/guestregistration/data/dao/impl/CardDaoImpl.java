package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.Mappers.CardMapper;
import eu.exadel.practice.guestregistration.data.Mappers.LocationMapper;
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
import java.util.stream.Collectors;


@Repository
public class CardDaoImpl implements CardDao {
    MongoTemplate mongoTemplate;
    CardMapper cardMapper;

    @Autowired
    public CardDaoImpl(MongoTemplate mongoTemplate, CardMapper cardMapper) {
        this.mongoTemplate = mongoTemplate;
        this.cardMapper = cardMapper;
    }

    @Override
    public void deleteAll() {
//        mongoTemplate.remove(Card.class);   <<--- this one doesn't remove Document data
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), CardEntity.class);
    }

    @Override
    public Optional<Card> findById(String id) {
        CardEntity cardEntity = mongoTemplate.findById(id, CardEntity.class);
        Card card = cardMapper.entityToDomain(cardEntity);
        return Optional.ofNullable(card);
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(CardEntity.class).stream().map( cardEntity ->
                cardMapper.entityToDomain(cardEntity)
        ).collect(Collectors.toList());
    }

    @Override
    public Card save(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        cardEntity.setId(ObjectId.get());
        return cardMapper.entityToDomain(mongoTemplate.save(cardEntity));
    }

    @Override
    public void update(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), CardEntity.class)) {
            mongoTemplate.save(cardEntity);
        }
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, CardEntity.class);
    }

    @Override
    public boolean existById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, CardEntity.class);
    }

    @Override
    public boolean exist(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        return mongoTemplate.exists(Query.query(Criteria.byExample(cardEntity)), CardEntity.class);
    }
}
