package practice.guestregistry.services.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.data.api.domain.Worker;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.service.WorkerService;
import practice.guestregistry.services.service.CardService;

import java.util.List;
import java.util.Optional;

@Service("mongoDb")
public class WorkerServiceImpl implements WorkerService {
    private WorkerDao workerDao;
    private PersonService personService;
    private CardService cardService;
//    private SequenceDao sequenceDao;
    private static final Logger log = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    @Override
    public void deleteAll () {
        workerDao.deleteAll();
    }

    @Override
    public Optional<Worker> getWorkerById (String id) {
        if (workerDao.existById(id)) {
            return Optional.of(workerDao.findById(id));
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    @Override
    public List<Worker> getAllWorkers () {
        return workerDao.findAll();
    }

    @Override
    public Worker saveWorker (Worker newWorker) {
        return workerDao.save(newWorker);
    }

    @Override
    public Worker updateWorker (Worker newWorker) {
        if (workerDao.existById(newWorker.getId())) {
            return workerDao.update(newWorker);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }

    @Override
    public void deleteWorkerById (String id) {
        if (workerDao.existById(id)) {
            workerDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }

    @Override
    public boolean existById(String id) {
        return workerDao.existById(id);
    }

    @Override
    public boolean exist(Worker worker) {
        if (worker == null) {
            return false;
        }
//        log.trace("worker Exist " + worker + " " +  practice.guestregistry.data.impl.workerDao.exists(Example.of(worker)));
//        return practice.guestregistry.data.impl.workerDao.exists(Example.of(worker));
        return workerDao.exist(worker);
    }

}
