package practice.guestregistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.models.Event;
import practice.guestregistry.services.EventService;

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
    public Optional<Event> getEvent(@PathVariable ObjectId id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<Event> getEvents() { return service.getAllEvents(); }

    @PostMapping
    public void addEvent(@RequestBody Event event) {
        service.addEvent(event); }

    @PutMapping
    public void updateEvent(@RequestBody Event event) { service.updateEvent(event); }

    @DeleteMapping(path="{id}")
    public void deleteEvent(@PathVariable ObjectId id) { service.deleteEventById(id); }
}
