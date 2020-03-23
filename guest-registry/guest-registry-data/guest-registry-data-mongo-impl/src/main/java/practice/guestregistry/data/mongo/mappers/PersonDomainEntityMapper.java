package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.domain.Person;

@Component
public class PersonDomainEntityMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public PersonDomainEntityMapper() {
//            PersonService workerService,
//            CardService cardService,
//            PersonService personService) {
//        this.workerService = workerService;
//        this.cardService = cardService;
//        this.personService = personService;

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(PersonEntity.class, Person.class)
                .exclude("id")
                .byDefault()
                .customize(new CustomMapper<PersonEntity, Person>() {
                    @Override
                    public void mapBtoA(Person personDomain, PersonEntity personEntity, MappingContext context) {
                        if (ObjectId.isValid(personDomain.getId())) {
                            personEntity.setId(new ObjectId(personDomain.getId()));
                            super.mapBtoA(personDomain, personEntity, context);
                        } else {
                            personEntity.setId(null);
                        }
                    }
                })
//        mapNulls(true).mapNullsInReverse(true).
                .register();
    }

    public Person map (PersonEntity personEntity) {
        Person mappedPerson = new Person();
        this.mapperFactory.getMapperFacade(PersonEntity.class, Person.class).map(personEntity, mappedPerson);
        return mappedPerson;
    }

    public PersonEntity map (Person personDomain) {
        PersonEntity mappedPersonEntity = new PersonEntity();
        this.mapperFactory.getMapperFacade(Person.class, PersonEntity.class).map(personDomain, mappedPersonEntity);
        return mappedPersonEntity;
    }
//    public void map (PersonDomain workerDTO, Person person) {
//        this.mapperFactory.getMapperFacade(PersonDomain.class, Person.class).map(workerDTO, person);
//    }
//    public void map (PersonDomain workerDTO, Card card) {
//        this.mapperFactory.getMapperFacade(PersonDomain.class, Card.class).map(workerDTO, card);
//    }

//    public <S, D> D map(S s, Class<D> type) {
//        return this.mapperFactory.getMapperFacade().map(s, type);
//    }
//public <Target> void map (PersonDomain workerDTO, Target mapTo) {
//    this.mapperFactory.getMapperFacade(PersonDomain.class, Target.class).map(workerDTO, mapTo);
//}


//    public <S, D> D map (S s, Class<D> target) {
//        return this.mapperFactory.getMapperFacade().map(s, target);
//    }
}
