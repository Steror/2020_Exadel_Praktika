package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.converter.WorkerDomainToPersonDomainMapper;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.data.api.domain.Card;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.data.api.domain.Worker;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
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
    private WorkerDomainToPersonDomainMapper mapper;
//    private SequenceDao sequenceDao;
    private static final Logger log = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(WorkerDao workerDao, PersonService personService, CardService cardService) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
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
        if (validCard(newWorker.getCardId(), newWorker.getCardSerialNumber())) {
            //person exist in db, if not add to db before adding worker to db
            Person person = mapper.map(newWorker);
            if (newWorker.getPersonId().isEmpty()) {
                personService.savePerson(person);
            } else if (!personService.personExist(person)) {
                throw new InvalidDocumentStateException("Incorrect person information to add worker");
            }

            return workerDao.save(newWorker);
        } else {
            throw new InvalidDocumentStateException("Incorrect card information to add worker");
        }
    }

    @Override
    public Worker updateWorker (Worker newWorker) {
        if (workerDao.existById(newWorker.getId())) {
            if (validCard(newWorker.getCardId(), newWorker.getCardSerialNumber())
                    && personService.getPersonById(newWorker.getPersonId()).isPresent()) {
                return workerDao.update(newWorker);
            } else {
                throw new InvalidDocumentStateException("Invalid card information || person by this id doesnt exist");
            }
        } else {
            throw new ResourceNotFoundException("This worker doesn't exist");
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


    private boolean validCard(String id, String serial) {
        if (id.isEmpty()) {
            return true;
        } else if (ObjectId.isValid(id)) {
            return false;
        } else {
            //TODO: ar sitas palyginimas tikrai veikia? :D
//            return cardService.getCardById(id) ==  cardService.getCardBySerial(serial);
            //                  |
            //TODO: remove this V
            return false;
        }
    }
}
