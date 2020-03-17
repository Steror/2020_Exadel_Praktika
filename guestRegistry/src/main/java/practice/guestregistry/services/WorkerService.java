package practice.guestregistry.services;

import practice.guestregistry.dto.WorkerDTO;

import java.util.List;
import java.util.Optional;

public interface WorkerService extends CrudService<WorkerDTO, String> {
    Optional<WorkerDTO> getWorkerById(String id);
    List<WorkerDTO> getAllWorkers();
}
