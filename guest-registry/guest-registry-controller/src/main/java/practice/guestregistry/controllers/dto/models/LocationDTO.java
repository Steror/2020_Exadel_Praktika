package practice.guestregistry.controllers.dto.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LocationDTO {
    private String id;
    private String name;
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    private String address;
    private String locationType;
    @Pattern(regexp = "[0-9]*")
    private String phoneNumber;
}
