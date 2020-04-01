package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.domain.Worker;
import practice.guestregistry.services.service.WorkerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/worker")
@CrossOrigin(origins = "http://localhost:4200")
@Api
public class WorkerController {
    private final WorkerService workerService;
    private static final Logger log = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    WorkerController(@Qualifier("mongodb") WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping(path="{id}")
    public Worker getWorker(@PathVariable String id) {
        return workerService.getWorkerById(id);
    }

    @ApiOperation(value = "View a list of workers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<Worker> getWorkers() {
//        log.trace("A TRACE Message");
//        log.debug("A DEBUG Message");
//        log.info("An INFO Message");
//        log.warn("A WARN Message");
//        log.error("An ERROR Message");
//        return workerService.getAllWorkers()
//                .stream()
//                .map(mapper::map)
//                .collect(Collectors.toList());
        return workerService.getAllWorkers();
    }

    @PostMapping
    public ResponseEntity<Worker> addWorker(@Valid @RequestBody Worker worker) {
        HttpHeaders responseHeaders = new HttpHeaders();
        workerService.addWorker(worker);
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(worker.getId())
                .toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(path="{id}")
//    public ResponseEntity<@Valid Worker> updateWorker(@Valid @RequestBody Worker newWorker) {
    public ResponseEntity<@Valid Worker> updateWorker(@Valid @RequestBody Worker worker) {
        log.trace("updating worker: " + worker);
        workerService.updateWorker(worker);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="{id}")
    public void deleteWorker(@PathVariable String id) {
        workerService.deleteWorkerById(id);
    }

}

