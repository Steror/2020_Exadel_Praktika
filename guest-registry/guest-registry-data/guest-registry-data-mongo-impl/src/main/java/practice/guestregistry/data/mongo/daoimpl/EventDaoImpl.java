package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.data.mongo.entities.EventEntity;
import practice.guestregistry.data.mongo.mappers.EventMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventDaoImpl implements EventDao {
    MongoTemplate mongoTemplate;
    EventMapper eventMapper;

    @Autowired
    public EventDaoImpl(MongoTemplate mongoTemplate, EventMapper eventMapper) {
        this.mongoTemplate = mongoTemplate;
        this.eventMapper = eventMapper;
    }

    @Override
    public Optional<Event> findById (String id) {
        return Optional.ofNullable(eventMapper.entityToDomain(mongoTemplate.findById(id, EventEntity.class)));
    }

    @Override
    public List<Event> findAll () {
        return mongoTemplate.findAll(EventEntity.class).stream().map(eventEntity ->
                eventMapper.entityToDomain(eventEntity)
        ).collect(Collectors.toList());
    }

    @Override
    public void add (Event event) {
        EventEntity eventEntity = eventMapper.domainToEntity(event);
        eventEntity.setId(ObjectId.get());
        mongoTemplate.save(eventEntity);
    }

    @Override
    public Event update (Event event) {
        EventEntity eventEntity = eventMapper.domainToEntity(event);
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), EventEntity.class)) {
            mongoTemplate.save(eventEntity);
        }
        return eventMapper.entityToDomain(eventEntity);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, EventEntity.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), EventEntity.class);
    }
}