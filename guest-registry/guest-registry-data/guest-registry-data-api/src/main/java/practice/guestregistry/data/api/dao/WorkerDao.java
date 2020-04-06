package practice.guestregistry.data.api.dao;

import practice.guestregistry.domain.User;
import practice.guestregistry.domain.Worker;

import java.util.List;

public interface WorkerDao {
    Worker findById (String id);
    List<Worker> findAll ();
    void add (Worker worker);
    void update(Worker worker);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Worker worker);
    boolean matchUser(User user);
}
