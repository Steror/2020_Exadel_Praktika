//package practice.guestregistry.controllers.api;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import practice.guestregistry.controllers.dto.mappers.WorkerDtoDomainMapper;
//import practice.guestregistry.controllers.dto.models.WorkerDTO;
//import practice.guestregistry.services.service.WorkerService;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("api/worker")
//@CrossOrigin(origins = "http://localhost:4200")
//@Api
//public class WorkerController {
//    private WorkerService workerService;
//    private WorkerDtoDomainMapper mapper;
//    private static final Logger log = LoggerFactory.getLogger(WorkerController.class);
//
//    @Autowired
//    WorkerController(@Qualifier("mongoDb") WorkerService workerService, WorkerDtoDomainMapper mapper) {
//        this.workerService = workerService;
//        this.mapper = mapper;
//    }
//
//    @GetMapping(path="{id}")
//    public Optional<WorkerDTO> getWorker(@PathVariable String id) {
//        return Optional.ofNullable(mapper.map(workerService.getWorkerById(id).get()));
//    }
//
//    @ApiOperation(value = "View a list of workers")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved list"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//    })
//    @GetMapping
//    public List<WorkerDTO> getWorkers() {
////        log.trace("A TRACE Message");
////        log.debug("A DEBUG Message");
////        log.info("An INFO Message");
////        log.warn("A WARN Message");
////        log.error("An ERROR Message");
//        return workerService.getAllWorkers()
//                .stream()
//                .map(mapper::map)
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping
//    public ResponseEntity<WorkerDTO> addWorker(@Valid @RequestBody WorkerDTO workerDTO) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        log.trace("workerDTO:" + workerDTO);
//        WorkerDTO savedWorkerDTO = mapper.map(workerService.saveWorker(mapper.map(workerDTO)));
//        log.trace("savedWorkerDTO:" + savedWorkerDTO);
//        responseHeaders.setLocation(ServletUriComponentsBuilder.
//                fromCurrentRequest().
//                path("/{id}").
//                buildAndExpand(savedWorkerDTO.getId())
//                .toUri());
//        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
//    }
//
//
//    @PutMapping(path="{id}")
////    public ResponseEntity<@Valid Worker> updateWorker(@Valid @RequestBody Worker newWorker) {
//    public ResponseEntity<@Valid WorkerDTO> updateWorker(@Valid @RequestBody WorkerDTO workerDTO) {
//        log.trace(workerDTO.toString());
//        log.trace("updating worker: " + workerDTO);
//        WorkerDTO updatedWorker = mapper.map(workerService.updateWorker(mapper.map(workerDTO)));
//        log.trace("updated worker: " + updatedWorker);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping(path="{id}")
//    public void deleteWorker(@PathVariable String id) {
//        workerService.deleteWorkerById(id);
//    }
//
//}
//
