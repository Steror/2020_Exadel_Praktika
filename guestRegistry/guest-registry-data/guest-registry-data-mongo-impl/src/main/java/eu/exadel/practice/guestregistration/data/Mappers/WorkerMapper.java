package eu.exadel.practice.guestregistration.data.Mappers;

import eu.exadel.practice.guestregistration.data.domain.Worker;
import eu.exadel.practice.guestregistration.data.entities.WorkerEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
