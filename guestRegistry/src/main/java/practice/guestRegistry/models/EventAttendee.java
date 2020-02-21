package practice.guestRegistry.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "eventsAttendees")
public class EventAttendee {
    @DBRef(db = "test")
    private Event event;
    @DBRef(db = "test")
    private Person person;
}
