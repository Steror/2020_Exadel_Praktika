package practice.guestregistry.data.api.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Card {
    private String id;
    @NotEmpty
    private String serialNumber;

    @NotNull
    private Location location;
    @NotNull
    private LocalDateTime manufactured;
    @NotNull
    private LocalDateTime validUntil;
    @NotNull
    private String ctype;
}
