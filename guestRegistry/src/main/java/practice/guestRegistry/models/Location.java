package practice.guestRegistry.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "location")
public class Location {
    @Id
    private Long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private LocationType locationType;
    private String phoneNumber;
    //Worker manager;
}
