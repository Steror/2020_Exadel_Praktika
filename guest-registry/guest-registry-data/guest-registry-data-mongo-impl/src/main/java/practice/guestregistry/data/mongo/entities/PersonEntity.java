package practice.guestregistry.data.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document("person")
@Document
@CompoundIndex(def = "{'firstName':1, 'middleName':1, 'lastName':1}", unique = true)
//@CompoundIndexes({@CompoundIndex(name = "name_middle_last", def = "{firstName : 1, middleName : 1, lastName:1", unique = true)})
public class PersonEntity {

    @Id
    private ObjectId id;
    private String firstName;
    private String middleName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
//    @Pattern(regexp = "[0-9]*")
    @Indexed(unique = true)
    private String phoneNumber;
    private int referenced = 0;
    //URL url;
//    TODO:ar tikrai sito reikia?
//    @DBRef(db = "test")
//    private List<Event> events;

}
