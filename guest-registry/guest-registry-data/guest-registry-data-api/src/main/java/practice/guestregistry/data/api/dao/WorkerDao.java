package practice.guestregistry.data.api.dao;

import practice.guestregistry.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerDao {
    Optional<Worker> findById (String id);
    List<Worker> findAll ();
    Worker add (Worker worker);
    Worker update(Worker worker);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Worker worker);
}
