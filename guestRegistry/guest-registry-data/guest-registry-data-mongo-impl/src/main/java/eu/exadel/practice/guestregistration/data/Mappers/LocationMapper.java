package eu.exadel.practice.guestregistration.data.Mappers;

import eu.exadel.practice.guestregistration.data.domain.Location;
import eu.exadel.practice.guestregistration.data.entities.LocationEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationMapper {
    private MapperFactory mapperFactory;
    private BoundMapperFacade<Location, LocationEntity> boundMapperFacade;

    @Autowired
    public LocationMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Location.class, LocationEntity.class);
    }

    public Location entityToDomain (LocationEntity locationEntity) {
        Location location = new Location();
        this.mapperFactory.getMapperFacade(LocationEntity.class, Location.class).map(locationEntity, location);
        return location;
    }

    public LocationEntity domainToEntity (Location location) {
        LocationEntity locationEntity = new LocationEntity();
        this.mapperFactory.getMapperFacade(Location.class, LocationEntity.class).map(location, locationEntity);
        return locationEntity;
    }
}
