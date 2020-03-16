package eu.exadel.practice.guestregistration.data.dao.impl;

import entities.LocationEntity;
import eu.exadel.practice.guestregistration.data.dao.LocationDao;
import eu.exadel.practice.guestregistration.data.domain.Location;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<LocationEntity> findById (ObjectId id) {
        return Optional.ofNullable(mongoTemplate.findById(id, LocationEntity.class));
    }

    @Override
    public Optional<Location> findById(String id) {
        LocationEntity entity = findById(new ObjectId(id)).get();
        Location domain = new Location();
        domain.setId(entity.getId().toString());

        return Optional.of(domain);
    }

//    public List<Location> findAll () {
//        return mongoTemplate.findAll(Location.class);
//    }

    @Override
    public Location add(Location location) {
        return null;
    }

    @Override
    public Location update(String id, Location location) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    public LocationEntity add (LocationEntity locationEntity) {
        //ObjectId temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        locationEntity.setId(ObjectId.get());
        mongoTemplate.save(locationEntity);
        return locationEntity;
    }

    public LocationEntity update (ObjectId id, LocationEntity locationEntity) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), LocationEntity.class)) {
            locationEntity.setId(id);
            mongoTemplate.save(locationEntity);
        }
        return locationEntity;
    }

    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, LocationEntity.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), LocationEntity.class);
    }
}
