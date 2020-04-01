package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.EventEntity;
import practice.guestregistry.domain.Event;

@Component
public class EventMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public EventMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Event.class, EventEntity.class);
    }

    public Event entityToDomain (EventEntity eventEntity) {
        Event event = new Event();
        this.mapperFactory.getMapperFacade(EventEntity.class, Event.class).map(eventEntity, event);
        return event;
    }

    public EventEntity domainToEntity (Event event) {
        EventEntity eventEntity = new EventEntity();
        this.mapperFactory.getMapperFacade(Event.class, EventEntity.class).map(event, eventEntity);
        return eventEntity;
    }
}
