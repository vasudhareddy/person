package com.embl.person.model;

import lombok.*;
import lombok.experimental.*;

@Data
@Builder
public class PersonRequest {

    private String firstName;
    private String lastName;
    private Integer age;
    private String favouriteColour;

    @Tolerate
    public PersonRequest() {

    }
}
