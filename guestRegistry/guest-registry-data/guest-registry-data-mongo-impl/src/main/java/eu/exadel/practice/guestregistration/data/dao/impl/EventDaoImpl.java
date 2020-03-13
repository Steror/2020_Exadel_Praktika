package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.dao.EventDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import entities.Event;

import java.util.List;
import java.util.Optional;

@Repository
public class EventDaoImpl implements EventDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public EventDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Event> findById (ObjectId id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Event.class));
    }

    public List<Event> findAll () {
        return mongoTemplate.findAll(Event.class);
    }

    public void add (Event event) {
        event.setId(ObjectId.get());
        mongoTemplate.save(event);
    }

    public void update (ObjectId id, Event event) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Event.class)) {
            event.setId(id);
            mongoTemplate.save(event);
        }
    }

    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Event.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), Event.class);
    }
}
