package com.embl.person.aspect;

import com.embl.person.model.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Aspect
@Component
public class LoggedInUserNameSettingAspect {

    @Before("@annotation(com.embl.person.response.LoggedInUserName) && args(loggedInUserName,..)")
    public  void userSetting(String  loggedInUserName) {
        User.setUser(loggedInUserName);
    }
}
