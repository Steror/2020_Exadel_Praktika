package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.WorkerDao;
import practice.guestregistry.dto.WorkerDTO;
import practice.guestregistry.dto.WorkerDTOMapper;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("mongoDb")
public class WorkerServiceImpl implements WorkerService {
    private WorkerDao workerDao;
    private PersonService personService;
    private CardService cardService;
    private WorkerDTOMapper workerDTOMapper;
    private static final Logger log = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(WorkerDao workerDao,
                             PersonService personService,
                             CardService cardService,
                             WorkerDTOMapper workerDTOMapper) {
        this.workerDao = workerDao;
        this.personService = personService;
        this.cardService = cardService;
        this.workerDTOMapper = workerDTOMapper;
    }

    public void deleteAll () {
        workerDao.deleteAll();
    }

    public Optional<WorkerDTO> getWorkerById (ObjectId id) {
        if (workerDao.existById(id)) {
            return convertToDto(workerDao.findById(id).get());
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public List<WorkerDTO> getAllWorkers () {
        return workerDao.
                findAll().
                stream().
                map( (elem) -> {
                    return convertToDto(elem).get();
                }).
                collect(Collectors.toList());
    }

    public WorkerDTO addWorker (WorkerDTO workerDTO) {
        Worker convertedToWorker = convertToWorker(workerDTO);

        if (validCard(convertedToWorker.getCard())
                && convertedToWorker.getPerson() != null) {
            //TODO: email should be different
            if (!personService.personExist(convertedToWorker.getPerson())) {
                Person addedPerson = personService.addPerson(convertedToWorker.getPerson());
                convertedToWorker.setPerson(addedPerson);
            }
            return convertToDto(workerDao.save(convertedToWorker)).get();
        } else {
            throw new InvalidDocumentStateException("Incorrect information to add worker");
        }
    }


    public WorkerDTO updateWorker (@NotNull WorkerDTO workerDTO) {
        Worker newWorker = convertToWorker(workerDTO);
        if (workerDao.existById( new ObjectId(workerDTO.getWorkerId()))) {
            if (newWorker.getPerson() != null
                    && personService.getPersonById(newWorker.getPerson().getId()).isPresent()
                    && validCard(newWorker.getCard())) {
                return convertToDto(workerDao.update(newWorker)).get();
            } else {
                throw new InvalidDocumentStateException("Invalid fields: person || card doesn't exist");
            }
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    public void deleteWorkerById (ObjectId id) {
        //log.trace("called:" + this.getClass().getEnclosingMethod().getName());
        if (workerDao.existById(id)) {
            workerDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Document by this id doesn't exist");
        }
    }

    private boolean validCard(Card card) {
        if (card == null) {
            return true;
        } else {
            return cardService.cardExist(card);
        }
    }

    private Optional<WorkerDTO> convertToDto (Worker worker) {
        if (worker != null) {
            return Optional.of(workerDTOMapper.map(worker));
        }
        return Optional.empty();
    }

    private Worker convertToWorker(WorkerDTO workerDTO) {
        log.trace("converting to worker got: " + workerDTO);
        return workerDTOMapper.map(workerDTO);
    }
}


