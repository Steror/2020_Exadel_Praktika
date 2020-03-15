package practice.guestregistry.data.impl.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.api.dto.PersonDTO;
import practice.guestregistry.data.impl.models.Person;

@Component
public class PersonDTOMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public PersonDTOMapper() {
//            PersonService workerService,
//            CardService cardService,
//            PersonService personService) {
//        this.workerService = workerService;
//        this.cardService = cardService;
//        this.personService = personService;

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Person.class, PersonDTO.class).
//                exclude("id").
//        mapNulls(true).mapNullsInReverse(true).
                byDefault().
                register();
    }

    public PersonDTO map (Person person) {
        PersonDTO mappedPerson = new PersonDTO();
        this.mapperFactory.getMapperFacade(Person.class, PersonDTO.class).map(person, mappedPerson);
        return mappedPerson;
    }

    public Person map (PersonDTO personDTO) {
        Person mappedPerson = new Person();
        this.mapperFactory.getMapperFacade(PersonDTO.class, Person.class).map(personDTO, mappedPerson);
        return mappedPerson;
    }
//    public void map (PersonDTO workerDTO, Person person) {
//        this.mapperFactory.getMapperFacade(PersonDTO.class, Person.class).map(workerDTO, person);
//    }
//    public void map (PersonDTO workerDTO, Card card) {
//        this.mapperFactory.getMapperFacade(PersonDTO.class, Card.class).map(workerDTO, card);
//    }

//    public <S, D> D map(S s, Class<D> type) {
//        return this.mapperFactory.getMapperFacade().map(s, type);
//    }
//public <Target> void map (PersonDTO workerDTO, Target mapTo) {
//    this.mapperFactory.getMapperFacade(PersonDTO.class, Target.class).map(workerDTO, mapTo);
//}


//    public <S, D> D map (S s, Class<D> target) {
//        return this.mapperFactory.getMapperFacade().map(s, target);
//    }
}
