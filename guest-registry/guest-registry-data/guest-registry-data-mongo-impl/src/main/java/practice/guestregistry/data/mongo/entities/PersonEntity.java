package practice.guestregistry.data.mongo.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestregistry.data.mongo.JsonSerializer.ObjectID_Serializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document("person")
public class PersonEntity {

    @Id
    @NotNull
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;

    @NotEmpty
    private String firstName;
    private String middleName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "[0-9]*")
    private String phoneNumber;
    //URL url;
    private Set<PersonRole> roles = new LinkedHashSet<>(1);

//    TODO:ar tikrai sito reikia?
//    @DBRef(db = "test")
//    private List<Event> events;

    public PersonEntity(ObjectId id, String firstName, String middleName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
