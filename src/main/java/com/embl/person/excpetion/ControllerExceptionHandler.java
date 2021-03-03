package com.embl.person.excpetion;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.time.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage personeNotFoundException(PersonNotFoundException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now()).message(ex.getMessage())
                .description(request.getDescription(false)).build();
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message =ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now()).message(ex.getMessage())
                .description(request.getDescription(false)).build();
        return message;
    }
}
