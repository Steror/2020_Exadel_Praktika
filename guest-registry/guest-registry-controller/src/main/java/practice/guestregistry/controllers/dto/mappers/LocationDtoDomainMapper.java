package practice.guestregistry.controllers.dto.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.controllers.dto.models.LocationDTO;
import practice.guestregistry.data.api.domain.Location;

@Component
public class LocationDtoDomainMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public LocationDtoDomainMapper() {

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(LocationDTO.class, Location.class)
                .byDefault()
                .register();
    }

    public LocationDTO map (Location location) {
        return this.mapperFactory.getMapperFacade(Location.class, LocationDTO.class).map(location);
    }

    public Location map (LocationDTO locationDTO) {
        return this.mapperFactory.getMapperFacade(LocationDTO.class, Location.class).map(locationDTO);
    }
}
