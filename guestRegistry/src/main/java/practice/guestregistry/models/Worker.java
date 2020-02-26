package practice.guestregistry.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestregistry.config.ObjectID_Serializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "worker")
public class Worker {
    @Id
//    @NotNull
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;
    @DBRef
//    @NotNull
    private Person person;
    @DBRef
    private Card card;
    // POSITION
}
