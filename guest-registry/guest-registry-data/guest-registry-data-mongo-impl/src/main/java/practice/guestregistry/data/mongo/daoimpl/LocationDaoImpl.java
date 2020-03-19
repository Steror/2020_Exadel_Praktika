package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.LocationDao;
import practice.guestregistry.data.api.domain.Location;
import practice.guestregistry.data.mongo.entities.LocationEntity;
import practice.guestregistry.data.mongo.mappers.LocationMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LocationDaoImpl implements LocationDao {
    private MongoTemplate mongoTemplate;
    private LocationMapper mapper;

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate, LocationMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    public Optional<Location> findById (String id) {
        LocationEntity locationDB = mongoTemplate.findById(new ObjectId(id), LocationEntity.class);
        return Optional.ofNullable(mapper.map(locationDB));
    }


    public List<Location> findAll () {
        List<LocationEntity> list = mongoTemplate.findAll(LocationEntity.class);
        System.out.println(list);
        return mongoTemplate.findAll(LocationEntity.class)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    public Location save (Location location) {
        LocationEntity mappedLocationEntity = mapper.map(location);
        mappedLocationEntity.setId(ObjectId.get());
        return mapper.map(mongoTemplate.save(mappedLocationEntity));
    }

    public Location update (Location location) {
        LocationEntity mappedLocationEntity = mapper.map(location);
        return mapper.map(mongoTemplate.save(mappedLocationEntity));
    }

    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(new ObjectId(id)));
        mongoTemplate.remove(query, LocationEntity.class);
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), LocationEntity.class);
    }

    public boolean exist(Location location) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(mapper.map(location))), LocationEntity.class);
    }

    public boolean existById(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(new ObjectId(id))), LocationEntity.class);
    }
}
