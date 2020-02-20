package practice.guestRegistry.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.guestRegistry.config.ObjectID_Serializer;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "event")
public class Event {
    @Id
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;
    private String name;
    private String description;
    private int participants_amount;
    private LocalDateTime start_date_time;
    private LocalDateTime end_date_time;
    @DBRef(db = "test", lazy = false)
    private Location location;
    //@DBRef
    //Worker event_owner;
}
