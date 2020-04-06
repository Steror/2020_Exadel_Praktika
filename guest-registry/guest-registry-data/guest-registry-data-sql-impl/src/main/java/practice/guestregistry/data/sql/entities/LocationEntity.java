package practice.guestregistry.data.sql.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
//@Table(name = "location")
@Table(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String address;
    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    @Column(name = "phone_number")
    private String phoneNumber;
        //Worker manager;
}
