package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.entities.WorkerEntity;
import practice.guestregistry.domain.Worker;

@Component
public class WorkerMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public WorkerMapper() {
//            WorkerService workerService,
//            CardService cardService,
//            PersonService personEntityService) {
//        this.workerService = workerService;
//        this.cardService = cardService;
//        this.personEntityService = personEntityService;

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(WorkerEntity.class, Worker.class)
                .exclude("id")
//        mapNulls(true).mapNullsInReverse(true).
//                byDefault().
//                field("id", "id").
                .field("personEntity.id", "personId")
                .field("cardEntity.id", "cardId")
                .field("cardEntity.serialNumber", "cardSerialNumber")
                .field("personEntity.firstName", "firstName")
                .field("personEntity.middleName", "middleName")
                .field("personEntity.lastName", "lastName")
                .field("personEntity.email", "email")
                .field("personEntity.phoneNumber", "phoneNumber")
                .customize(new CustomMapper<WorkerEntity, Worker>() {
                    @Override
                    public void mapAtoB(WorkerEntity workerEntity, Worker worker, MappingContext context) {
                        worker.setCardId(workerEntity.getId().toHexString());
                    }
                    @Override
                    public void mapBtoA(Worker worker, WorkerEntity workerEntity, MappingContext context) {
                        if (ObjectId.isValid(worker.getId())) {
                            workerEntity.setId(new ObjectId(worker.getId()));
                            super.mapBtoA(worker, workerEntity, context);
                        } else {
                            workerEntity.setId(null);
                        }
                    }
                })
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
//    public void map (WorkerDTO workerDTO, Person personEntity) {
//        this.mapperFactory.getMapperFacade(WorkerDTO.class, Person.class).map(workerDTO, personEntity);
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
