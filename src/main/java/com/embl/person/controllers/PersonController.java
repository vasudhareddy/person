package com.embl.person.controllers;

import com.embl.person.entity.*;
import com.embl.person.model.*;
import com.embl.person.response.*;
import com.embl.person.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/save")
    @LoggedInUserName
    public Person createPerson(@RequestHeader(name = "loggedInUserName",required = true) String loggedInUserName,
                               @RequestBody PersonRequest personRequest) {
        return personService.savePerson(personRequest);
    }

    @PostMapping
    public List<Person> createPersons(@RequestHeader(name = "loggedInUserName",required = true) String loggedInUserName,
                                      @RequestBody List<PersonRequest> personRequestList) {
        return personService.savePersons(personRequestList);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.fetchPersons();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable(name = "id") Long id) {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable(name = "id") Long id) {
         personService.deletePerson(id);
        return "Deleted Successfully";
    }

    @PutMapping("/{id}")
    public Person updatePerson(@RequestHeader(name = "loggedInUserName") String loggedInUserName,
                               @PathVariable(name = "id") Long id,
                               @RequestBody PersonRequest personRequest) {
        return personService.updatePerson(id,personRequest);
    }

}
