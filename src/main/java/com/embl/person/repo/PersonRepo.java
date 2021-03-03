package com.embl.person.repo;

import com.embl.person.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PersonRepo extends JpaRepository<Person, Long> {
    //List<Person> findPersonByPersonId(List<Long> ids);
}
