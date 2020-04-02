package practice.guestregistry.dao;

import org.bson.types.ObjectId;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;


public interface WorkerDao {
    void deleteAll ();
    Optional<Worker> findById (ObjectId id);
    List<Worker> findAll ();
    Worker save (Worker worker);
    Worker update(Worker worker);
    void deleteById (ObjectId id);
    boolean existById(ObjectId id);
}

