package practice.guestregistry.data.api.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Event {
    private String id;
    private String name;
    private String description;
    private int participants_amount;
    @NotNull
    private LocalDateTime start_date_time;
    @NotNull
    private LocalDateTime end_date_time;
    @NotNull
    private Location location;
    @NotNull
    private List<Person> attendees;
}
