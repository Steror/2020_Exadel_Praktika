package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "worker")
@Document
public class WorkerEntity {
    @Id
    private ObjectId id;
    @NotNull
    @DBRef
//    @CascadeSave
    private PersonEntity personEntity;
    @DBRef
    private CardEntity cardEntity;
    // POSITION
}