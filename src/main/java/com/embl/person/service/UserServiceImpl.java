package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.model.*;
import com.embl.person.repo.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity saveUser(UserRequest userRequest) {
        UserEntity userEntity = mapper.convertValue(userRequest, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

}
