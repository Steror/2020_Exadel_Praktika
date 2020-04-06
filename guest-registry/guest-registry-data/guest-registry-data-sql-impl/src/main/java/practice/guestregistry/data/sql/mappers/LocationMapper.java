package practice.guestregistry.data.sql.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import practice.guestregistry.data.sql.entities.LocationEntity;
import practice.guestregistry.domain.Location;

//@Component
public class LocationMapper {
    private MapperFactory mapperFactory;

    public LocationMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Location.class, LocationEntity.class);
    }

    public Location entityToDomain(LocationEntity locationEntity) {
        return this.mapperFactory.getMapperFacade(LocationEntity.class, Location.class).map(locationEntity);
    }

    public LocationEntity domainToEntity(Location location) {
        return this.mapperFactory.getMapperFacade(Location.class, LocationEntity.class).map(location);
    }
}

