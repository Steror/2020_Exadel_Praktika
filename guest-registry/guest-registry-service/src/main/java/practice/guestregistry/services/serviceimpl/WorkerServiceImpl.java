package practice.guestregistry.services.serviceimpl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.mongo.mappers.WorkerDomainToPersonDomainMapper;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.domain.Person;
import practice.guestregistry.domain.User;
import practice.guestregistry.domain.Worker;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.PersonService;
import practice.guestregistry.services.service.WorkerService;
import practice.guestregistry.services.service.CardService;

import java.util.List;

@Service("mongodb")
public class WorkerServiceImpl implements WorkerService {
    private WorkerDao workerDao;
    private PersonService personService;
    private CardService cardService;
    private WorkerDomainToPersonDomainMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(WorkerDao workerDao, PersonService personService, CardService cardService) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
    }


    @Override
    public Worker getWorkerById (String id) {
        if (id == null) {
            throw new ResourceNotFoundException("id null");
        }
        return workerDao.findById(id);
    }

    @Override
    public List<Worker> getAllWorkers () {
        return workerDao.findAll();
    }

    @Override
    public void addWorker (Worker newWorker) {
        if (validCard(newWorker.getCardId(), newWorker.getCardSerialNumber())) {
            //person exist in db, if not add to db before adding worker to db
            Person person = mapper.map(newWorker);
            if ((newWorker.getPersonId() == null || newWorker.getPersonId().isEmpty())
                    && !personService.personExist(person)) {
                personService.addPerson(person);
            } else {
                throw new InvalidDocumentStateException("Incorrect person information to add worker");
            }
            workerDao.add(newWorker);
        } else {
            throw new InvalidDocumentStateException("Incorrect card information to add worker");
        }
    }

    @Override
    public void updateWorker (Worker newWorker) {
        if (workerDao.existById(newWorker.getId())) {
            if (validCard(newWorker.getCardId(), newWorker.getCardSerialNumber())
//                    && personService.getPersonById(newWorker.getPersonId()).isPresent()) {
                    && personService.existById(newWorker.getPersonId())) {
                workerDao.update(newWorker);
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
    public void deleteAllWorkers () {
        workerDao.deleteAll();
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
        return cardService.existCardContainingIdSerial(id, serial);
    }

    public boolean matchUser(User user) {
        return workerDao.matchUser(user);
    }
}
