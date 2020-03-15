package practice.guestregistry.data.impl.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import practice.guestregistry.data.impl.JsonSerializer.ObjectID_Serializer;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "location")
public class Location {
    @Id
    @JsonSerialize(using = ObjectID_Serializer.class)
    private ObjectId id;
    private String name;
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String address;
    private LocationType locationType;
    private String phoneNumber;
    //Worker manager;
}
