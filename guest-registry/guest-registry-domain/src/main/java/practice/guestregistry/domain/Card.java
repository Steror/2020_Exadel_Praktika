package practice.guestregistry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String id;
    @NotEmpty
    private String serialNumber;
    @NotEmpty
    private String locationId;
    private String locationName;
    @NotEmpty
    private String manufactured;
    @NotEmpty
    private String validUntil;
    @NotEmpty
    private String ctype;
}
