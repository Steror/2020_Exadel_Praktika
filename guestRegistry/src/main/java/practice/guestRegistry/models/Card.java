package practice.guestRegistry.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestRegistry.config.ObjectID_Serializer;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "card")
public class Card {

    @Id
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;

    @NotEmpty
    private String serialNumber;

    @DBRef(db = "test", lazy = false)
    @NonNull
    private Location location;
    private LocalDateTime manufactured;
    private LocalDateTime validUntil;
//    @JsonProperty("ctype")
    private CardType ctype;

    public Card(ObjectId id, String serialNumber, Location location, LocalDateTime manufactured, LocalDateTime validUntil, CardType ctype) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.location = location;
        this.manufactured = manufactured;
        this.validUntil = validUntil;
        this.ctype = ctype;
    }
}
