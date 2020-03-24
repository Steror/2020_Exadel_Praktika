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
