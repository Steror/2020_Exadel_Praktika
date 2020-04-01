package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.LocationEntity;
import practice.guestregistry.domain.Location;

@Component
public class LocationMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public LocationMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Location.class, LocationEntity.class);
    }

    public Location entityToDomain(LocationEntity locationEntity) {
        Location location = new Location();
        this.mapperFactory.getMapperFacade(LocationEntity.class, Location.class).map(locationEntity, location);
        return location;
    }

    public LocationEntity domainToEntity(Location location) {
        LocationEntity locationEntity = new LocationEntity();
        this.mapperFactory.getMapperFacade(Location.class, LocationEntity.class).map(location, locationEntity);
        return locationEntity;
    }

}

