package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerService {
    public void deleteAll ();
    public Optional<Worker> getWorkerById (String id);
    public List<Worker> getAllWorkers ();
    public Worker saveWorker (Worker newWorker);
    public Worker updateWorker (Worker newWorker);
    public void deleteWorkerById (String id);
    public boolean exist(Worker worker);
    public boolean existById(String id);

}
