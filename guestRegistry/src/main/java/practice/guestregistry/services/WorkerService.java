package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.WorkerDao;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService implements IBasicService<Worker>{
    WorkerDao workerDao;
    PersonService personService;
    CardService cardService;
    Logger log = LoggerFactory.getLogger(WorkerService.class);

    @Autowired
    public WorkerService (WorkerDao workerDao,
                          PersonService personService,
                          CardService cardService) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
    }

    public void deleteAll () {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        workerDao.deleteAll();
    }

    public Optional<Worker> getWorkerById (ObjectId id) {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        if (workerDao.existById(id)) {
            return workerDao.findById(id);
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public List<Worker> getAllWorkers () {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        return workerDao.findAll();
    }

    public Worker addWorker (Worker newWorker) {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        if (validateWorkerFields(newWorker)) {
            return workerDao.save(newWorker);
        } else {
            throw new InvalidDocumentStateException("Invalid fields: person || card doesn't exist");
        }
    }

    public Worker updateWorker (@NotNull Worker newWorker) {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        if (workerDao.existById(newWorker.getId())) {
            if (validateWorkerFields(newWorker)) {
                return workerDao.update(newWorker);
            } else {
                throw new InvalidDocumentStateException("Invalid fields: person || card doesn't exist");
            }
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public void deleteWorkerById (ObjectId id) {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        if (workerDao.existById(id)) {
            workerDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    //person must exist
    //if card exist it should be valid
    private boolean validateWorkerFields(@NotNull Worker newWorker) {
        log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        log.trace("validating worker: " + newWorker);
        Optional<Person> personInDb = personService.getPersonById(newWorker.getPerson().getId());
        if (newWorker.getPerson() != null && personInDb.isPresent()) {
            log.trace("personInDb found by worker.person.id: " + personInDb.get());
            if (newWorker.getCard() == null) {
                return true;
            } else {
                //card exist and its valid
                Optional<Card> cardInDb = cardService.getCardById(newWorker.getCard().getId());
                if (cardInDb.isPresent()) {
                    log.trace("cardInDb found by worker.card.id: " + cardInDb.get());
                    return true;
                }
            }
        }
        return false;
    }
}

