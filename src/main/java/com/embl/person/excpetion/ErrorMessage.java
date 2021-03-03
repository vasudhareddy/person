package com.embl.person.excpetion;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Data
@Builder
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
    @Tolerate
    public  ErrorMessage() {

    }
}
