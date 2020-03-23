package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.data.api.domain.Worker;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.entities.WorkerEntity;

@Component
public class WorkerMapper {
    private MapperFactory mapperFactory;

    @Autowired
    //TODO:fix this mappint workerEntity person.id on WorkerEntity doesnt exist
    public WorkerMapper() {
//            WorkerService workerService,
//            CardService cardService,
//            PersonService personService) {
//        this.workerService = workerService;
//        this.cardService = cardService;
//        this.personService = personService;

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(WorkerEntity.class, Worker.class)
                .exclude("id")
//        mapNulls(true).mapNullsInReverse(true).
//                byDefault().
//                field("id", "id").
//                .field("person.id", "personId")
//                .field("card.id", "cardId")
//                .field("card.serialNumber", "cardSerialNumber")
//                .field("person.firstName", "firstName")
//                .field("person.middleName", "middleName")
//                .field("person.lastName", "lastName")
//                .field("person.email", "email")
//                .field("person.phoneNumber", "phoneNumber")
//                .customize(new CustomMapper<WorkerEntity, Worker>() {
//                    @Override
//                    public void mapBtoA(Worker worker, WorkerEntity workerEntity, MappingContext context) {
//                        if (ObjectId.isValid(worker.getId())) {
//                            workerEntity.setId(new ObjectId(worker.getId()));
//                            super.mapBtoA(worker, workerEntity, context);
//                        } else {
//                            workerEntity.setId(null);
//                        }
//                    }
//                })
                .register();
    }

    //TODO: elaborate mapping method name
    public Worker map (WorkerEntity worker) {
//        Worker target = new Worker();
//        this.mapperFactory.getMapperFacade(WorkerEntity.class, Worker.class).map(worker, target);
//        this.mapperFactory.getMapperFacade(Person.class, WorkerDTO.class).map(worker.getPerson(), target);
//        this.mapperFactory.getMapperFacade(Card.class, WorkerDTO.class).map(worker.getCard(), target);
//        return target;
        return this.mapperFactory.getMapperFacade(WorkerEntity.class, Worker.class).map(worker);
    }

    //TODO: elaborate mapping method name
    public WorkerEntity map (Worker worker) {
        return this.mapperFactory.getMapperFacade(Worker.class, WorkerEntity.class).map(worker);
//        Worker mappedWorker = new Worker();
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Worker.class).map(workerDTO, mappedWorker);
//        return mappedWorker;
    }
//    public void map (WorkerDTO workerDTO, Person person) {
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Person.class).map(workerDTO, person);
//    }
//    public void map (WorkerDTO workerDTO, Card card) {
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Card.class).map(workerDTO, card);
//    }

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
