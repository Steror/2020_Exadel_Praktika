package practice.guestregistry.services;

import java.util.List;
import java.util.Optional;

public interface CrudService <Entity, ID> {
    Optional<Entity> getWorkerById (ID id);
    List<Entity> getAllWorkers ();
    Entity addWorker (Entity newEntity);
    Entity updateWorker (Entity newEntity);
    void deleteWorkerById (ID id);
    void deleteAll();
}


