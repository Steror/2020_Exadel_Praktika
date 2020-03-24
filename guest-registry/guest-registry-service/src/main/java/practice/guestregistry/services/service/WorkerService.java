package practice.guestregistry.services.service;


import practice.guestregistry.domain.Worker;

import java.util.List;

public interface WorkerService {
    Worker getWorkerById (String id);
    List<Worker> getAllWorkers ();
    Worker addWorker (Worker worker);
    Worker updateWorker (Worker worker);
    void deleteWorkerById (String id);
    void deleteAllWorkers ();
    boolean exist(Worker worker);
    boolean existById(String id);

}
