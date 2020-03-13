package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.dao.LocationDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import entities.Location;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Location> findById (ObjectId id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Location.class));
    }

    @Override
    public Optional<eu.exadel.practice.guestregistration.data.domain.Location> findById(String id) {
        Location entity = findById(new ObjectId(id)).get();
        eu.exadel.practice.guestregistration.data.domain.Location domain = new eu.exadel.practice.guestregistration.data.domain.Location();
        domain.setId(entity.getId().toString());

        return Optional.of(domain);
    }

//    public List<Location> findAll () {
//        return mongoTemplate.findAll(Location.class);
//    }

    @Override
    public eu.exadel.practice.guestregistration.data.domain.Location add(eu.exadel.practice.guestregistration.data.domain.Location location) {
        return null;
    }

    @Override
    public eu.exadel.practice.guestregistration.data.domain.Location update(String id, eu.exadel.practice.guestregistration.data.domain.Location location) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    public Location add (Location location) {
        //ObjectId temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        location.setId(ObjectId.get());
        mongoTemplate.save(location);
        return location;
    }

    public Location update (ObjectId id, Location location) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Location.class)) {
            location.setId(id);
            mongoTemplate.save(location);
        }
        return location;
    }

    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Location.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), Location.class);
    }
}
