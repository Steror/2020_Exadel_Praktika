package practice.guestregistry.data.api.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.data.api.domain.Worker;

@Component
public class WorkerDomainToPersonDomainMapper {
    private MapperFactory mapperFactory;

    public WorkerDomainToPersonDomainMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Worker.class, Person.class)
                .exclude("id")
                .byDefault()
                .field("personId", "id")
                .register();
    }

    //TODO: elaborate mapping method name
    public Worker map (Person person) {
        return this.mapperFactory.getMapperFacade(Person.class, Worker.class).map(person);
    }

    //TODO: elaborate mapping method name
    public Person map (Worker worker) {
        return this.mapperFactory.getMapperFacade(Worker.class, Person.class).map(worker);
    }
}
