package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.WorkerDao;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService implements IBasicService<Worker>{
    WorkerDao workerDao;
    PersonService personService;
    CardService cardService;

    @Autowired
    public WorkerService (WorkerDao workerDao,
                          PersonService personService,
                          CardService cardService) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
    }

    public void deleteAll () {
        workerDao.deleteAll();
    }

    public Optional<Worker> getWorkerById (ObjectId id) {
        if (workerDao.existById(id)) {
            return workerDao.findById(id);
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public List<Worker> getAllWorkers () {
        return workerDao.findAll();
    }

    public Worker addWorker (Worker newWorker) {
        if (validateWorkerFields(newWorker)) {
            return workerDao.save(newWorker);
        } else {
            throw new InvalidDocumentStateException("Invalid fields: person || card doesn't exist");
        }
    }

    public void updateWorker (Worker newWorker) {
        if (workerDao.existById(newWorker.getId())) {
            if (validateWorkerFields(newWorker)) {
                workerDao.save(newWorker);
            } else {
                throw new InvalidDocumentStateException("Invalid fields: person || card doesn't exist");
            }
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public void deleteWorkerById (ObjectId id) {
        if (workerDao.existById(id)) {
            workerDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    //person must exist
    //if card exist it should be valid
    private boolean validateWorkerFields(Worker newWorker) {
        if (newWorker.getPerson() != null &&
                personService.getPersonById(newWorker.getPerson().getId()).isPresent()) {
            if (newWorker.getCard() == null) {
                return true;
            } else {
                //card exist and its valid
                if (cardService.getCardById(newWorker.getCard().getId()).isPresent()) {
                    return true;
                }
            }
        }
        return false;
    }
}






















//    public Worker addWorker (Worker newWorker) {
//        if (newWorker.getPerson() != null &&
//                personService.getPersonById(newWorker.getPerson().getId()).isPresent()) {
//            if (newWorker.getCard() == null) {
//                return workerDao.save(newWorker);
//            } else {
//                //card exist
//                if (cardService.getCardById(newWorker.getCard().getId()).isPresent()) {
//                    return workerDao.save(newWorker);
//                }
//            }
//        }
//        return null; // or it should throw exception
//    }
//
//    public void updateWorker (Worker newWorker) {
//        if (workerDao.existsById(newWorker.getId())) {
//            workerDao.save(newWorker);
//        } else {
//            throw new ResourceNotFoundException("Document by this id doesn't exist");
//        }
//    }
