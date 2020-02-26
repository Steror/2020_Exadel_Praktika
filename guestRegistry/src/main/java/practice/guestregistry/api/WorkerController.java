package practice.guestregistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Worker;
import practice.guestregistry.services.WorkerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//@RestController("api/worker")
@RestController
@RequestMapping("api/worker")
public class WorkerController {
    private WorkerService workerService;

    @Autowired
    WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping(path="{id}")
    public Optional<Worker> getWorker(@PathVariable ObjectId id) {
            return workerService.getWorkerById(id);
    }

    @GetMapping
    public List<Worker> getWorkers() {
        return workerService.getAllWorkers();
    }

    @PostMapping
    public ResponseEntity<Card> addWorker(@Valid @RequestBody Worker person) {
        Worker savedWorker = workerService.addWorker(person);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedWorker.getId().toString())
                .toUri());
        return new ResponseEntity<Card>(null, responseHeaders, HttpStatus.CREATED);
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
}

