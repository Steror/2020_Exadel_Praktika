package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "event")
public class EventEntity {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private int participantsAmount;
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @DBRef(db = "test")
    @NotNull
    private LocationEntity location;
    @DBRef(db = "test")
    @NotNull
    private List<PersonEntity> attendees;
    //@DBRef
    //Worker event_owner;
}
