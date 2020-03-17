//package practice.guestregistry.data.impl.mappers;
//
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.DefaultMapperFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import practice.guestregistry.data.api.dto.WorkerDTO;
//import practice.guestregistry.data.impl.models.Card;
//import practice.guestregistry.data.impl.models.Person;
//import practice.guestregistry.data.impl.models.Worker;
//
//@Component
//public class WorkerDTOMapper {
//    private MapperFactory mapperFactory;
//
//    @Autowired
//    public WorkerDTOMapper() {
////            WorkerService workerService,
////            CardService cardService,
////            PersonService personService) {
////        this.workerService = workerService;
////        this.cardService = cardService;
////        this.personService = personService;
//
//        this.mapperFactory = new DefaultMapperFactory.Builder().build();
//        this.mapperFactory.classMap(Worker.class, WorkerDTO.class).
////                exclude("id").
////        mapNulls(true).mapNullsInReverse(true).
////                byDefault().
//                field("id", "workerId").
//                field("person.id", "personId").
//                field("card.id", "cardId").
//                field("card.serialNumber", "cardSerialNumber").
//                field("person.firstName", "firstName").
//                field("person.middleName", "middleName").
//                field("person.lastName", "lastName").
//                field("person.email", "email").
//                field("person.phoneNumber", "phoneNumber").
//                register();
//    }
//
//    public WorkerDTO map (Worker worker) {
//        WorkerDTO target = new WorkerDTO();
//        this.mapperFactory.getMapperFacade(Worker.class, WorkerDTO.class).map(worker, target);
//        this.mapperFactory.getMapperFacade(Person.class, WorkerDTO.class).map(worker.getPerson(), target);
//        this.mapperFactory.getMapperFacade(Card.class, WorkerDTO.class).map(worker.getCard(), target);
//        return target;
//    }
//
//    public Worker map (WorkerDTO workerDTO) {
////        worker.setId(new ObjectId(workerDTO.getId()));
////        personService. // find this person by name, midle, and last
//        Worker mappedWorker = new Worker();
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Worker.class).map(workerDTO, mappedWorker);
//        return mappedWorker;
////        this.mapperFactory.getMapperFacade(WorkerDTO.class, Card.class).map(workerDTO, worker.getCard());
////        return worker;
//    }
////    public void map (WorkerDTO workerDTO, Person person) {
////        this.mapperFactory.getMapperFacade(WorkerDTO.class, Person.class).map(workerDTO, person);
////    }
////    public void map (WorkerDTO workerDTO, Card card) {
////        this.mapperFactory.getMapperFacade(WorkerDTO.class, Card.class).map(workerDTO, card);
////    }
//
////    public <S, D> D map(S s, Class<D> type) {
////        return this.mapperFactory.getMapperFacade().map(s, type);
////    }
////public <Target> void map (WorkerDTO workerDTO, Target mapTo) {
////    this.mapperFactory.getMapperFacade(WorkerDTO.class, Target.class).map(workerDTO, mapTo);
////}
//
//
////    public <S, D> D map (S s, Class<D> target) {
////        return this.mapperFactory.getMapperFacade().map(s, target);
////    }
//}
