package eu.exadel.practice.guestregistration.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    private String id;
    @NotNull
    private Person person;
    private Card card;
}
