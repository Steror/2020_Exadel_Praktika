package practice.guestRegistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.guestRegistry.exceptions.ResourceNotFoundException;
import practice.guestRegistry.models.Worker;
import practice.guestRegistry.services.WorkerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//@RestController("api/worker")
@RestController
@RequestMapping("api/worker")
public class WorkerController {
    private WorkerService service;

    @Autowired
    WorkerController(WorkerService service) {
        this.service = service;
    }


    @GetMapping(path="{id}")
    public Optional<Worker> getWorker(@PathVariable ObjectId id) {
        if (service.getWorkerById(id).isPresent()) {
            return service.getWorkerById(id);
        } else {
            throw new ResourceNotFoundException("Worker with id " + id + " not found");
        }
    }

    @GetMapping
    public List<Worker> getWorkers() {
        return service.getAllWorkers();
    }

    @PostMapping
    public void addWorker(@Valid @RequestBody Worker person) {
        service.addWorker(person);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<@Valid Worker> updateWorker(@PathVariable ObjectId id, @Valid @RequestBody Worker newWorker) {
        if (service.getWorkerById(newWorker.getId()).isPresent()) {
            service.updateWorker(newWorker);
            return new ResponseEntity<>(newWorker, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Worker with id " + id + " not found");
        }
        //or return null;
    }

    @DeleteMapping(path="{id}")
    public void deleteWorker(@PathVariable ObjectId id) {
        service.deleteWorkerById(id);
    }
}
