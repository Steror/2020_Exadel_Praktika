package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.mongo.entities.CardEntity;
import practice.guestregistry.data.mongo.entities.LocationEntity;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CardDaoImpl implements CardDao {

    private final MongoTemplate mongoTemplate;
    private final CardMapper cardMapper;
    private final LocationDao locationDao;
    private static final Logger log = LoggerFactory.getLogger(CardDaoImpl.class);

    @Autowired
    public CardDaoImpl(MongoTemplate mongoTemplate, CardMapper cardMapper, LocationDao locationDao) {
        this.mongoTemplate = mongoTemplate;
        this.cardMapper = cardMapper;
        this.locationDao = locationDao;
    }

    @Override
    public Card findById(String id) {
        log.trace("[findById] ("+id+")");
        CardEntity cardEntity = mongoTemplate.findById(id, CardEntity.class);
        log.trace("[findById] mongo response: " + cardEntity);
        if (cardEntity == null) {
            throw new ResourceNotFoundException("Card by this id doens't exist");
        } else {
            Card card = cardMapper.entityToDomain(cardEntity);
            log.trace("[findById] returning card after mapping: " + card);
            return card;
        }
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(CardEntity.class).stream().map(cardMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Card card) {
        log.debug("[add] card before mapping" + card);
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        log.debug("[add] card after mapping" + cardEntity);
        cardEntity.setId(ObjectId.get());

//        LocationEntity locationFromDb = mongoTemplate.findById(Criteria.where("id").is(card.getLocationId()), LocationEntity.class);
        LocationEntity locationFromDb = mongoTemplate.findById(card.getLocationId(), LocationEntity.class);
        cardEntity.setLocationEntity(locationFromDb);

//        cardEntity.setLocationEntity(mongoTemplate.f);
        log.debug("[add] card before save" + cardEntity);
        CardEntity savedCard = mongoTemplate.save(cardEntity);
        if (savedCard == null) {
//            throw new EntityCreationException();
        } else {
            card.setId(savedCard.getId().toHexString());
        }
    }

    @Override
    public void update(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        CardEntity updatedCard = null;
        updatedCard = mongoTemplate.save(cardEntity);
        if (updatedCard == null) {
//            throw new UpdateException();
        }
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        CardEntity deletedEntity = mongoTemplate.findAndRemove(query, CardEntity.class);
        if (deletedEntity == null) {
//            throw new DeletionException();
        }
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
