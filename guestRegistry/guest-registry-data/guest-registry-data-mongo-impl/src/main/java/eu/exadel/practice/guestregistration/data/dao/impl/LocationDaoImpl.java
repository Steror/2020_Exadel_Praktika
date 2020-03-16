package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.Mappers.LocationMapper;
import eu.exadel.practice.guestregistration.data.entities.LocationEntity;
import eu.exadel.practice.guestregistration.data.dao.LocationDao;
import eu.exadel.practice.guestregistration.data.domain.Location;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LocationDaoImpl implements LocationDao {
    MongoTemplate mongoTemplate;
    LocationMapper locationMapper;

    @Autowired
    public LocationDaoImpl(MongoTemplate mongoTemplate, LocationMapper locationMapper) {
        this.mongoTemplate = mongoTemplate;
        this.locationMapper = locationMapper;
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

    @Override
    public List<Location> findAll () {
        return mongoTemplate.findAll(LocationEntity.class).stream().map( locationEntity ->
             locationMapper.entityToDomain(locationEntity)
        ).collect(Collectors.toList());
    }

    @Override
    public Location add (Location location) {
        LocationEntity locationEntity = this.locationMapper.domainToEntity(location);
        locationEntity.setId(ObjectId.get());
        mongoTemplate.save(locationEntity);
        location = this.locationMapper.entityToDomain(locationEntity);
        return location;
    }

    @Override
    public Location update (String id, Location location) {
        LocationEntity locationEntity = this.locationMapper.domainToEntity(location);
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), LocationEntity.class)) {
            locationEntity.setId(new ObjectId(id));
            mongoTemplate.save(locationEntity);
        }
        location = this.locationMapper.entityToDomain(locationEntity);
        return location;
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(new ObjectId(id)));
        mongoTemplate.remove(query, LocationEntity.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), LocationEntity.class);
    }
}
