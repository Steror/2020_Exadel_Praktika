package practice.guestRegistry.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestRegistry.models.Location;
import practice.guestRegistry.models.SequenceId;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    MongoTemplate mongoTemplate;
    SequenceDao sequenceDao;
    private static final String HOSTING_SEQ_KEY = "location";

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate, SequenceDao sequenceDao) {
        this.mongoTemplate = mongoTemplate;
        this.sequenceDao = sequenceDao;
    }

    public Optional<Location> findById (ObjectId id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Location.class));
    }

    public List<Location> findAll () {
        return mongoTemplate.findAll(Location.class);
    }

    public void add (Location location) {
        //ObjectId temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        location.setId(ObjectId.get());
        mongoTemplate.save(location);
    }

    public void update (ObjectId id, Location location) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Location.class)) {
            location.setId(id);
            mongoTemplate.save(location);
        }
    }

    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Location.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), Location.class);
    }

    @PostConstruct
    private void after() {
        if (!mongoTemplate.collectionExists(Location.class)) {
            sequenceDao.initCollection(HOSTING_SEQ_KEY);
        }
        System.out.println("--------------------------------");
        for (SequenceId name : mongoTemplate.findAll(SequenceId.class)) {
            System.out.println(name);
        }
        System.out.println("--------------------------------");
    }
}
