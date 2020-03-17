package practice.guestregistry.api;

import eu.exadel.practice.guestregistration.data.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.services.EventService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {
    EventService service;

    @Autowired
    public EventController(EventService service) { this.service = service; }

    @GetMapping(path="{id}")
    public Optional<Event> getEvent(@PathVariable String id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<Event> getEvents() { return service.getAllEvents(); }

    @PostMapping
    public void addEvent(@Valid @RequestBody Event event) {
        service.addEvent(event); }

    @PutMapping //Pakeist update event request angular front-end
    public void updateEvent(@Valid @RequestBody Event event) { service.updateEvent(event); }

    @DeleteMapping(path="{id}")
    public void deleteEvent(@PathVariable String id) { service.deleteEventById(id); }
}
