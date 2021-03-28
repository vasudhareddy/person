package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.model.*;

public interface UserService {
    UserEntity saveUser(UserRequest userRequest);
}
