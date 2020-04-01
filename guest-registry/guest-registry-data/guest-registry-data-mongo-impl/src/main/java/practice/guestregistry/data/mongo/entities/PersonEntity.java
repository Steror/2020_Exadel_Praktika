package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document("person")
@Document
@CompoundIndex(def = "{'firstName':1, 'middleName':1, 'lastName':1}", unique = true)
//@CompoundIndexes({@CompoundIndex(name = "name_middle_last", def = "{firstName : 1, middleName : 1, lastName:1", unique = true)})
public class PersonEntity {

//    @NotNull
//    @JsonSerialize(using = ObjectID_Serializer.class)
    @Id
    private ObjectId id;

//    @NotEmpty
    private String firstName;
    private String middleName;
//    @NotEmpty
    private String lastName;
//    @Email
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
