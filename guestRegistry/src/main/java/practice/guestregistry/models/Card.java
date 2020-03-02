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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "card")
public class Card {

    @Id
//    @NotNull
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;

    @NotEmpty
    private String serialNumber;

    @DBRef(db = "test", lazy = false)
//    @NotNull
    private Location location;
    @NotNull
    private LocalDateTime manufactured;
    @NotNull
    private LocalDateTime validUntil;
    @NotNull
    private CardType ctype;
//    @JsonProperty("ctype")

}
