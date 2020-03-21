package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestregistry.data.mongo.JsonSerializer.ObjectID_Serializer;
import practice.guestregistry.data.mongo.annotations.CascadeSave;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "worker")
public class WorkerEntity {
    @Id
    @NotNull
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;
    @DBRef
    @NotNull
    @CascadeSave
    private PersonEntity personEntity;
    @DBRef
    private CardEntity cardEntity;
    // POSITION
}