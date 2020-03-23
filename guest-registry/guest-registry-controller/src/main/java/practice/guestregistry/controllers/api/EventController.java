package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.data.api.domain.Event;
import practice.guestregistry.services.service.EventService;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService service) { this.service = service; }

    @GetMapping(path="{id}")
    public Event getEvent(@PathVariable String id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<Event> getEvents() { return service.getAllEvents(); }

    @PostMapping
    public void addEvent(@RequestBody Event event) {
        service.addEvent(event); }

    @PutMapping(path="{id}")
    public void updateEvent(@RequestBody Event event) { service.updateEvent(event); }

    @DeleteMapping(path="{id}")
    public void deleteEvent(@PathVariable String id) { service.deleteEventById(id); }
}
