package entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import annotations.CascadeSave;
import config.ObjectID_Serializer;

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
