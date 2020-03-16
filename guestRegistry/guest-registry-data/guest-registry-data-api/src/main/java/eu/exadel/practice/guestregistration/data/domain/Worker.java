package eu.exadel.practice.guestregistration.data.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Worker {
    private String id;
    @NotNull
    private Person person;
    private Card card;
}
