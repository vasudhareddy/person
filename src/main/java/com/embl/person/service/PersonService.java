package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.model.*;

import java.util.*;

public interface PersonService {
    Person savePerson(PersonRequest personRequest);
    List<Person> savePersons(List<PersonRequest> personRequest);
    Person updatePerson(Long personId,PersonRequest personRequest);
    void deletePerson(Long personId);
    List<Person> fetchPersons();
    Person getPersonById(Long personId);
//    List<Person> fetchSpecificPersons(List<Long> personIds);
}
