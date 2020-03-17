package practice.guestregistry.controllers.dto.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.controllers.dto.models.WorkerDTO;
import practice.guestregistry.data.api.domain.Worker;

@Component
public class WorkerDtoDomainMapper {
    private MapperFactory mapperFactory;
    @Autowired
    public WorkerDtoDomainMapper() {

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(WorkerDTO.class, Worker.class)
                .byDefault()
                .register();
    }

    public WorkerDTO map (Worker worker) {
        return this.mapperFactory.getMapperFacade(Worker.class, WorkerDTO.class).map(worker);
    }

    public Worker map (WorkerDTO workerDTO) {
        return this.mapperFactory.getMapperFacade(WorkerDTO.class, Worker.class).map(workerDTO);
    }
}
