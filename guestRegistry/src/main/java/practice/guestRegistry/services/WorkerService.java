package practice.guestRegistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestRegistry.dao.WorkerDao;
import practice.guestRegistry.exceptions.DocumentNotFound;
import practice.guestRegistry.models.Worker;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    WorkerDao workerDao;
    PersonService personService;
    CardService cardService;

    @Autowired
    //person service or person dao?
    public WorkerService (WorkerDao workerDao,
                          PersonService personService,
                          CardService cardService
    ) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
    }

    public void deleteAll () {
        workerDao.deleteAll();
    }

    public Optional<Worker> getWorkerById (ObjectId id) {
        return workerDao.findById(id);
    }

    public List<Worker> getAllWorkers () {
        return workerDao.findAll();
    }

    public Worker addWorker (Worker newWorker) {
        if (newWorker.getPerson() != null &&
                personService.getPersonById(newWorker.getPerson().getId()).isPresent()) {
            //card provided and its valid
            if (newWorker.getCard() != null &&
                    cardService.getCardById(newWorker.getCard().getId()).isPresent()) {
                return workerDao.save(newWorker);
            } else {
                return workerDao.save(newWorker);
            }
        }
        return null; // or it should throw exception
    }

    public void updateWorker (Worker newWorker) {
        //posible check here if it allready exist
        if (workerDao.existsById(newWorker.getId())) {
            workerDao.save(newWorker);
        } else {
            throw new DocumentNotFound("Elem doesn't exist");
        }
    }

    public void deleteWorkerById (ObjectId id) {
        if (workerDao.existsById(id)) {
            workerDao.deleteById(id);
        } else {
            throw new DocumentNotFound("Elem doesn't exist");
        }
    }
}
