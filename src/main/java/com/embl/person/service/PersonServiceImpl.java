package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.excpetion.*;
import com.embl.person.model.*;
import com.embl.person.repo.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepo personRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public Person savePerson(PersonRequest personRequest) {
        Person person = objectMapper.convertValue(personRequest, Person.class);
        return personRepo.save(person);
    }

    @Override
    @Transactional
    public List<Person> savePersons(List<PersonRequest> personRequestList) {
        List<Person> personList = new ArrayList<>();
        personRequestList.forEach(r -> {
            personList.add(Person.builder().firstName(r.getFirstName()).lastName(r.getLastName()).age(r.getAge()).favouriteColour(r.getFavouriteColour()).build());
        });
        return personRepo.saveAll(personList);
    }

    @Override
    @Transactional
    public Person updatePerson(Long personId, PersonRequest request) {
        Optional<Person> person = personRepo.findById(personId);
        if (!person.isPresent())
            throw new PersonNotFoundException("No person available with id: " + personId);
        Person updatedPersonFromRequest = objectMapper.convertValue(request, Person.class);
        updatedPersonFromRequest.setPersonId(person.get().getPersonId());
        return personRepo.save(updatedPersonFromRequest);

    }

    @Override
    @Transactional
    public void deletePerson(Long personId) {
        personRepo.deleteById(personId);
    }

    @Override
    public List<Person> fetchPersons() {
        return personRepo.findAll();
    }

    @Override
    public Person getPersonById(Long personId) {
        Optional<Person> person = personRepo.findById(personId);
        if (!person.isPresent())
            throw new PersonNotFoundException("No person available with id: " + personId);
        return person.get();
    }


}
