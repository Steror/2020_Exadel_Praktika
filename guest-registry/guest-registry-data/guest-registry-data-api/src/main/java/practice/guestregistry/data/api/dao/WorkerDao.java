package practice.guestregistry.data.api.dao;

import practice.guestregistry.data.api.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerDao {
    void deleteAll ();
    Worker findById (String id);
    List<Worker> findAll ();
    Worker save (Worker worker);
    Worker update(Worker worker);
    void deleteById (String id);
    boolean existById(String id);
    boolean exist(Worker worker);
}
