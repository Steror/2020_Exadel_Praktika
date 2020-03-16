package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.dao.EventDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import entities.EventEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class EventDaoImpl implements EventDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public EventDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<EventEntity> findById (ObjectId id) {
        return Optional.ofNullable(mongoTemplate.findById(id, EventEntity.class));
    }

    public List<EventEntity> findAll () {
        return mongoTemplate.findAll(EventEntity.class);
    }

    public void add (EventEntity eventEntity) {
        eventEntity.setId(ObjectId.get());
        mongoTemplate.save(eventEntity);
    }

    public void update (ObjectId id, EventEntity eventEntity) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), EventEntity.class)) {
            eventEntity.setId(id);
            mongoTemplate.save(eventEntity);
        }
    }

    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, EventEntity.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), EventEntity.class);
    }
}
