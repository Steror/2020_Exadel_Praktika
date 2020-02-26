package practice.guestregistry.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import practice.guestregistry.config.ObjectID_Serializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@Document("person")
public class Person {

    @Id
    @NonNull
    @JsonSerialize(using = ObjectID_Serializer.class)
    ObjectId id;

    @NotEmpty
    String firstName;
    String middleName;
    @NotEmpty
    String lastName;
    @Email
    String email;
    @Pattern(regexp = "[0-9]*")
    String phoneNumber;
    //URL url;
    @DBRef(db = "test")
    private List<Event> events;

    public Person(ObjectId id, String firstName, String middleName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
