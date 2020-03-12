package practice.guestregistry.services;

import org.bson.types.ObjectId;
import practice.guestregistry.dto.WorkerDTO;

import java.util.List;
import java.util.Optional;

public interface WorkerService extends CrudService<WorkerDTO, ObjectId> {
    Optional<WorkerDTO> getWorkerById(ObjectId id);
    List<WorkerDTO> getAllWorkers();
}
