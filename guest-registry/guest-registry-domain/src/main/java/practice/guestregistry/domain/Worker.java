package practice.guestregistry.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;

@Data
public class Worker {
    @NotEmpty
    public String id;
//    @NotEmpty
    public String personId;
    @NotEmpty
    public String firstName;
    public String middleName;
    @NotEmpty
    public String lastName;
    @Email
    public String email;
    @Pattern(regexp = "[0-9]*")
    public String phoneNumber;
    @NotEmpty
    public String cardId;
    @NotEmpty
    public String cardSerialNumber;

    //smth with position
    //position started
    //termination date
    //Position string
}
