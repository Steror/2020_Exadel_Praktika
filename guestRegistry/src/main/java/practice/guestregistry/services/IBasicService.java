package practice.guestregistry.services;

import org.bson.types.ObjectId;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;

public interface IBasicService <Entity> {
    public void deleteAll();
    public Optional<Entity> getWorkerById (ObjectId id);
    public List<Entity> getAllWorkers ();
    public Worker addWorker (Entity newEntity);
    public void updateWorker (Entity newEntity);
    public void deleteWorkerById (ObjectId id);
}


