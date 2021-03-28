package com.embl.person.controllers;

import com.embl.person.entity.*;
import com.embl.person.model.*;
import com.embl.person.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userDetailsService;

    @PostMapping
    public UserEntity createUser(@RequestBody UserRequest userRequest) {
        return userDetailsService.saveUser(userRequest);
    }
}
