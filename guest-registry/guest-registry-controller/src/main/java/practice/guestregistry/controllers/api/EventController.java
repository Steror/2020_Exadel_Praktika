package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.services.serviceimpl.EventServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200")
@Api
public class EventController {
    EventServiceImpl service;

    @Autowired
    public EventController(EventServiceImpl service) { this.service = service; }

    @GetMapping(path="{id}")
    public Optional<Event> getEvent(@PathVariable String id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<Event> getEvents() { return service.getAllEvents(); }

    @PostMapping
    public void addEvent(@Valid @RequestBody Event event) {
        service.addEvent(event); }

    @PutMapping(path="{id}") // let it be :D
    public void updateEvent(@Valid @RequestBody Event event) { service.updateEvent(event); }

    @DeleteMapping(path="{id}")
    public void deleteEvent(@PathVariable String id) { service.deleteEventById(id); }
}
