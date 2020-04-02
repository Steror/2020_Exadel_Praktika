package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "card")
@Document
public class CardEntity {

    @Id
    private ObjectId id;
    private String serialNumber;

    @DBRef(db = "test", lazy = false)
    private LocationEntity locationEntity;
    private LocalDateTime manufactured;
    private LocalDateTime validUntil;
    private CardType ctype;
}
