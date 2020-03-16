package eu.exadel.practice.guestregistration.data.dao;

import eu.exadel.practice.guestregistration.data.domain.Worker;

import java.util.List;
import java.util.Optional;


public interface WorkerDao {
    void deleteAll ();
    Optional<Worker> findById (String id);
    List<Worker> findAll ();
    Worker save (Worker worker);
    Worker update(Worker worker);
    void deleteById (String id);
    boolean existById(String id);
}

