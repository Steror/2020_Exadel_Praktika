package practice.guestregistry.data.mongo.daoimpl;

import com.mongodb.client.result.DeleteResult;
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
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.mappers.CardMapper;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.*;

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
        }
        return cardMapper.entityToDomain(cardEntity);
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(CardEntity.class)
                .stream()
                .map(cardMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Card card) {
        log.debug("[add] card before mapping" + card);
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        log.debug("[add] card after mapping" + cardEntity);
        if (cardEntity.getId() != null) {
            throw new EntityCreationException("id must be null");
        }

        //fully reconstruct Card Entity
        LocationEntity locationFromDb = mongoTemplate.findById(card.getLocationId(), LocationEntity.class);
        cardEntity.setLocationEntity(locationFromDb);

        log.debug("[add] card before save" + cardEntity);
        CardEntity savedCard = mongoTemplate.save(cardEntity);
        card.setId(savedCard.getId().toHexString());
    }

    @Override
    public void update(Card card) {
        CardEntity cardEntity = cardMapper.domainToEntity(card);
        if (cardEntity.getId() == null) {
            throw new EntityUpdateException("id must be provided");
        }
        mongoTemplate.save(cardEntity);
    }

    @Override
    public void deleteById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        DeleteResult deletedWorker = mongoTemplate.remove(query, CardEntity.class);
        if (!deletedWorker.wasAcknowledged() || deletedWorker.getDeletedCount() == 0) {
            throw new ResourceNotFoundException("Cannot delete card by this id doesnt exist");
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

    @Override
    public boolean serialNumberExist(String serialNumber) {
        return mongoTemplate.exists(Query.query(Criteria.where("serialNumber").is(serialNumber)), CardEntity.class);
    }

    @Override
    public boolean existCardContainingIdSerial(String id, String serial) {
        Query query = Query.query(Criteria.where("serialNumber").is(serial).and("id").is(id));
        return mongoTemplate.exists(query, CardEntity.class);
    }
}
