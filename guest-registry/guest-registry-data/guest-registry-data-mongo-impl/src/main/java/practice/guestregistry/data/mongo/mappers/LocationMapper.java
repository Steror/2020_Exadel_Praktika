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

    public Location map(LocationEntity locationEntity) {
        return this.mapperFactory.getMapperFacade(LocationEntity.class, Location.class).map(locationEntity);
    }

    public LocationEntity map(Location location) {
        return this.mapperFactory.getMapperFacade(Location.class, LocationEntity.class).map(location);
    }

}

