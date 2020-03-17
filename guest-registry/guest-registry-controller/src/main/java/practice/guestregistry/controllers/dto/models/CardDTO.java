package practice.guestregistry.controllers.dto.models;

import lombok.Data;
import practice.guestregistry.data.api.domain.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CardDTO {

    private String id;
    @NotEmpty
    private String serialNumber;
    @NotEmpty
    private String locationId;
    @NotEmpty
    private String manufactured;
    @NotEmpty
    private String validUntil;
    @NotEmpty
    private String ctype;
}
