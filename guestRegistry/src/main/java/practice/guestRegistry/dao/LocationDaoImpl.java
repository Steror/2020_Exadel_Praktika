package practice.guestRegistry.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestRegistry.models.Location;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate) {this.mongoTemplate = mongoTemplate;}

    public Optional<Location> findById (Long id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Location.class));
    }

    public List<Location> findAll () {
        return mongoTemplate.findAll(Location.class);
    }

    public void save (Location location) {
        mongoTemplate.save(location);
    }

    public void deleteById(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Location.class);
    }
}
