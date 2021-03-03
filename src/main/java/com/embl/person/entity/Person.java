package com.embl.person.entity;

import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
@Builder
public class Person extends AuditObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable = false,nullable = false)
    private Long personId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String favouriteColour;

    @Tolerate
    public Person() {

    }


}
