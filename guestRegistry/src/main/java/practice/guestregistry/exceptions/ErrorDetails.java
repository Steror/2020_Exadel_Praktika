package practice.guestregistry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private int errorCode;
    private String errorMessage;
    private String devErrorMessage;
    private Date timeStamp;
    private Map<String, Object> additionalData = new HashMap<>();
//    private Map<String, List<ValidationError>> errors = new HashMap<String,
//            List<ValidationError>>();

}
