package practice.guestregistry.tdo;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;
import practice.guestregistry.services.CardService;
import practice.guestregistry.services.PersonService;
import practice.guestregistry.services.WorkerService;

@Component
public class WorkerDTOMapper {
    private MapperFactory mapperFactory;

    private WorkerService workerService;
    private CardService cardService;
    private PersonService personService;

    @Autowired
    public WorkerDTOMapper() {
//            WorkerService workerService,
//            CardService cardService,
//            PersonService personService) {
//        this.workerService = workerService;
//        this.cardService = cardService;
//        this.personService = personService;

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Worker.class, WorkerDTO.class).
//                exclude("id").
//        mapNulls(true).mapNullsInReverse(true).
//                byDefault().
                field("id", "workerId").
                field("person.id", "personId").
                field("card.id", "cardId").
                field("card.serialNumber", "cardSerialNumber").
                field("person.firstName", "firstName").
                field("person.middleName", "middleName").
                field("person.lastName", "lastName").
                field("person.email", "email").
                field("person.phoneNumber", "phoneNumber").
                register();
//        this.mapperFactory.classMap(Person.class, WorkerDTO.class).
//                exclude("id").
//                byDefault().register();
//        this.mapperFactory.classMap(Worker.class, WorkerDTO.class).
////                mapNulls(false).
//                mapNullsInReverse(false).
//                byDefault().
//                field("id", "id").
//                register();
//        this.mapperFactory.classMap(Card.class, WorkerDTO.class).
//                field("serialNumber", "cardSerialNumber").
//                field("id", "cardId").
//                register();
//
//        this.mapperFactory.classMap(WorkerDTO.class, Worker.class).
//                byDefault().
//                field("personId", "person.id").
//                field("cardId", "card.id").
//                field("id", "id").
//                register();
    }

//    public <S, D> D map(S s, Class<D> type) {
//        return this.mapperFactory.getMapperFacade().map(s, type);
//    }

    public WorkerDTO map (Worker worker) {
        WorkerDTO target = new WorkerDTO();
        this.mapperFactory.getMapperFacade(Worker.class, WorkerDTO.class).map(worker, target);
        this.mapperFactory.getMapperFacade(Person.class, WorkerDTO.class).map(worker.getPerson(), target);
        this.mapperFactory.getMapperFacade(Card.class, WorkerDTO.class).map(worker.getCard(), target);
        return target;
    }

    public void map (WorkerDTO workerDTO, Worker worker) {
//        worker.setId(new ObjectId(workerDTO.getId()));
//        personService. // find this person by name, midle, and last

        this.mapperFactory.getMapperFacade(WorkerDTO.class, Worker.class).map(workerDTO, worker);
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Card.class).map(workerDTO, worker.getCard());
//        return worker;
    }
    public void map (WorkerDTO workerDTO, Person person) {
        this.mapperFactory.getMapperFacade(WorkerDTO.class, Person.class).map(workerDTO, person);
    }
    public void map (WorkerDTO workerDTO, Card card) {
        this.mapperFactory.getMapperFacade(WorkerDTO.class, Card.class).map(workerDTO, card);
    }

//    public <S, D> D map(S s, Class<D> type) {
//        return this.mapperFactory.getMapperFacade().map(s, type);
//    }
//public <Target> void map (WorkerDTO workerDTO, Target mapTo) {
//    this.mapperFactory.getMapperFacade(WorkerDTO.class, Target.class).map(workerDTO, mapTo);
//}


//    public <S, D> D map (S s, Class<D> target) {
//        return this.mapperFactory.getMapperFacade().map(s, target);
//    }
}
