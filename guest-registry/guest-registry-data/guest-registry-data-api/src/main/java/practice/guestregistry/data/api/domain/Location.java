package practice.guestregistry.data.api.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Location {

    private String id;
    private String name;
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String address;
    private String locationType;
    private String phoneNumber;
}
