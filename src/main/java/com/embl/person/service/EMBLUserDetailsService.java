package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.model.*;
import com.embl.person.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class EMBLUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByName(userName);
        if (!userEntity.isPresent())
            throw new UsernameNotFoundException("User Not available");
        return new CustomUserDetails(userEntity.get());
    }
}
