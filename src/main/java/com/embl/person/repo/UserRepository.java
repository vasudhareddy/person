package com.embl.person.repo;

import com.embl.person.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByName(String userName);
}
