package com.embl.person.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

import javax.persistence.*;
import java.io.*;
import java.time.*;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public class AuditObject implements Serializable {

    private static final long serialVersionUID = -8319275215197409853L;

    @CreatedBy
    @Column(name = "created_by",nullable = false,updatable = false)
    @JsonIgnore
    private String createdBy;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_date",nullable = false,updatable = false)
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdDate;

    @Column(name="modified_date")
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
