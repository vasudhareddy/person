package com.embl.person.response;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD})
public @interface LoggedInUserName {
}
