package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import practice.guestregistry.data.api.domain.Worker;
import practice.guestregistry.data.mongo.entities.WorkerEntity;

public class WorkerMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public WorkerMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Worker.class, WorkerEntity.class);
    }

    public Worker entityToDomain (WorkerEntity workerEntity) {
        Worker worker = new Worker();
        this.mapperFactory.getMapperFacade(WorkerEntity.class, Worker.class).map(workerEntity, worker);
        return worker;
    }

    public WorkerEntity domainToEntity (Worker worker) {
        WorkerEntity workerEntity = new WorkerEntity();
        this.mapperFactory.getMapperFacade(Worker.class, WorkerEntity.class).map(worker, workerEntity);
        return workerEntity;
    }
}
