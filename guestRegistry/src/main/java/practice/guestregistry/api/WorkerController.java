package practice.guestregistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.models.Worker;
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
    private WorkerDTOMapper workerDTOMapper;

    @Autowired
    WorkerController(WorkerService workerService,
                     WorkerDTOMapper workerDTOMapper) {
        this.workerService = workerService;
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
//        Worker savedWorker = workerService.addWorker(worker);
        Worker reverseMapped = reverseMap(workerDTO);
        Worker savedWorker = workerService.addWorker(reverseMapped);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedWorker.getId().toString())
                .toUri());
        return new ResponseEntity<Worker>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(path="{id}")
//    public ResponseEntity<@Valid Worker> updateWorker(@PathVariable ObjectId id, @Valid @RequestBody Worker newWorker) {
    public ResponseEntity<@Valid Worker> updateWorker(@Valid @RequestBody Worker newWorker) {
        workerService.updateWorker(newWorker);
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

    private Worker reverseMap(WorkerDTO workerDTO) {
        System.out.println("Im getting this :D");
        System.out.println(workerDTO);
        Worker worker = new Worker();
        System.out.println("new and improved\n" + worker);
        return worker;
    }
}

