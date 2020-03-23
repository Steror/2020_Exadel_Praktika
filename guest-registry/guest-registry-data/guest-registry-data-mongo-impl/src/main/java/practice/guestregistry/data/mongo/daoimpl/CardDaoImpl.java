package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.api.domain.Card;
import practice.guestregistry.data.mongo.entities.CardEntity;
import practice.guestregistry.data.mongo.mappers.CardMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardDaoImpl implements CardDao {

    private final MongoTemplate mongoTemplate;
    private final CardMapper cardMapper;

    @Autowired
    public CardDaoImpl(MongoTemplate mongoTemplate, CardMapper cardMapper) {
        this.mongoTemplate = mongoTemplate;
        this.cardMapper = cardMapper;
    }

    @Override
    public Optional<Card> findById(String id) {
        CardEntity cardEntity = mongoTemplate.findById(id, CardEntity.class);
        Card card = cardMapper.entityToDomain(cardEntity);
        return Optional.ofNullable(card);
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(CardEntity.class).stream().map(cardMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Card add(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        cardEntity.setId(ObjectId.get());
        return cardMapper.entityToDomain(mongoTemplate.save(cardEntity));
    }

    @Override
    public Card update(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), CardEntity.class)) {
            mongoTemplate.save(cardEntity);
        }
        return cardMapper.entityToDomain(cardEntity);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, CardEntity.class);
    }

    @Override
    public void deleteAll() {
//        mongoTemplate.remove(Card.class);   <<--- this one doesn't remove Document data
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), CardEntity.class);
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
