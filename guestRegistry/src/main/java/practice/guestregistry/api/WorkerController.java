package practice.guestregistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;
import practice.guestregistry.services.PersonService;
import practice.guestregistry.services.WorkerService;
import practice.guestregistry.tdo.WorkerDTO;
import practice.guestregistry.tdo.WorkerDTOMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RestController("api/worker")
@RestController
@RequestMapping("api/worker")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkerController {
    private WorkerService workerService;
    private PersonService personService;
    private WorkerDTOMapper workerDTOMapper;

    @Autowired
    WorkerController(WorkerService workerService,
                     PersonService personService,
                     WorkerDTOMapper workerDTOMapper) {
        this.workerService = workerService;
        this.personService = personService;
        this.workerDTOMapper = workerDTOMapper;
    }

    @GetMapping(path="{id}")
    public Optional<WorkerDTO> getWorker(@PathVariable ObjectId id) {
        return Optional.ofNullable(
                convertToDto(workerService.getWorkerById(id).get())
        );
    }

    @GetMapping
    public List<WorkerDTO> getWorkers() {
//        System.out.println(workerService.getAllWorkers().get(0));
        return workerService.
                getAllWorkers().
                stream().
                map(this::convertToDto).
                collect(Collectors.toList());
    }

    @PostMapping
//    public ResponseEntity<Worker> addWorker(@Valid @RequestBody Worker worker) {
    public ResponseEntity<Worker> addWorker( @RequestBody WorkerDTO workerDTO) {
        HttpHeaders responseHeaders = new HttpHeaders();
//        Worker savedWorker = workerService.addWorker(worker);
        Worker convertedToWorker = convertToWorker(workerDTO);
//        System.out.println("convertedToWorker:" + convertedToWorker);

        ObjectId personId = convertedToWorker.getPerson().getId();
        if ( personId == null) {
            Person addedPerson = personService.addPerson(convertedToWorker.getPerson());
//            System.out.println("added person: " + addedPerson);
            convertedToWorker.setPerson(addedPerson);
//            System.out.println("convertedToWorker:" + convertedToWorker);
            Worker savedWorker = workerService.addWorker(convertedToWorker);
            responseHeaders.setLocation(ServletUriComponentsBuilder.
                    fromCurrentRequest().
                    path("/{id}").
                    buildAndExpand(savedWorker.getId().toString())
                    .toUri());
//            System.out.println("savedWorker:" + savedWorker);
        } else if (!personService.getPersonById(personId).isPresent()) {
            throw new InvalidDocumentStateException("This person doesnt exist");
        } else {
            //person exist
            //TODO: should person be updated?? probably not
        }


        return new ResponseEntity<Worker>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(path="{id}")
//    public ResponseEntity<@Valid Worker> updateWorker(@Valid @RequestBody Worker newWorker) {
    public ResponseEntity<@Valid WorkerDTO> updateWorker(@Valid @RequestBody WorkerDTO workerDTO) {
        System.out.println("ALOCHAAA");
        System.out.println(workerDTO);
        Worker worker = new Worker();
        workerDTOMapper.map(workerDTO, worker);

        System.out.println("updating worker: " + worker);
        Worker updatedWorker = workerService.updateWorker(worker);
        System.out.println("updated worker: " + updatedWorker);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="{id}")
    public void deleteWorker(@PathVariable ObjectId id) {
        workerService.deleteWorkerById(id);
    }

    private WorkerDTO convertToDto (Worker worker) {
        if (worker != null) {
            return workerDTOMapper.map(worker);
        }
        return null;
    }

    private Worker convertToWorker(WorkerDTO workerDTO) {
        System.out.println("convert to worker");
        System.out.println(workerDTO);
        Worker worker = new Worker();
        workerDTOMapper.map(workerDTO, worker);
        return worker;
    }
}

