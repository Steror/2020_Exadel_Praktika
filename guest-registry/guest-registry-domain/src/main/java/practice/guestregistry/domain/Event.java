package practice.guestregistry.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Event {
    private String id;
    private String name;
    private String description;
    private int participantsAmount;
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @NotNull
    private Location location;
    @NotNull
    private List<Person> attendees;
}
