package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestregistry.data.mongo.JsonSerializer.ObjectID_Serializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "event")
public class EventEntity {
    @Id
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;
    private String name;
    private String description;
    private int participants_amount;
    @NotNull
    private LocalDateTime start_date_time;
    @NotNull
    private LocalDateTime end_date_time;
    @DBRef(db = "test")
    @NotNull
    private LocationEntity locationEntity;
    @DBRef(db = "test")
    @NotNull
    private List<PersonEntity> attendees;
    //@DBRef
    //Worker event_owner;
}
