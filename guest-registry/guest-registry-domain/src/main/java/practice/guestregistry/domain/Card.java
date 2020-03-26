package practice.guestregistry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String id;
    private String serialNumber;
    @NotNull
    private String locationId;
    private String locationName;
    @NotNull
    private String manufactured;
    @NotNull
    private String validUntil;
    @NotNull
    private String ctype;
}
