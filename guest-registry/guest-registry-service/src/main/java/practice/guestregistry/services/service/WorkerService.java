package practice.guestregistry.services.service;


import practice.guestregistry.domain.Worker;

import java.util.List;

public interface WorkerService {
    Worker getWorkerById (String id);
    List<Worker> getAllWorkers ();
    void addWorker (Worker worker);
    void updateWorker (Worker worker);
    void deleteWorkerById (String id);
    void deleteAllWorkers ();
    boolean exist(Worker worker);
    boolean existById(String id);

}
