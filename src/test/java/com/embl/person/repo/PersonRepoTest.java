package com.embl.person.repo;

import com.embl.person.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class PersonRepoTest {

    @Autowired
    private PersonRepo personRepo;

    Person person1;
    Person person2;

    @BeforeEach
    public void setUp() {
        person1 = Person.builder().firstName("firstName1").lastName("lastName1").favouriteColour("pink1").age(10).build();
        person1.setCreatedBy("testUser1");
        person1.setCreatedDate(LocalDateTime.now());
        person2 = Person.builder().firstName("firstName2").lastName("lastName2").favouriteColour("pink2").age(10).build();
        person2.setCreatedBy("2");
        person2.setCreatedDate(LocalDateTime.now());
    }

    @Test
    public void testNotNull() {
        assertNotNull(personRepo);
    }

    @Test
    public void testSave() {
        Person savedPerson = personRepo.saveAndFlush(person1);
        List<Person> personList = personRepo.saveAll(Arrays.asList(person2));
        assertEquals(person1.getFirstName(), savedPerson.getFirstName());
        assertEquals(person2.getFirstName(), personList.get(0).getFirstName());
    }

    @Test
    public void testUpdate() {
        Person savedPerson = personRepo.saveAndFlush(person1);
        Optional<Person> person = personRepo.findById(savedPerson.getPersonId());
        person.get().setAge(25);
        Person updatedPerson = personRepo.saveAndFlush(person.get());
        assertEquals(25, updatedPerson.getAge().intValue());
    }

    @Test
    public void testDelete() {
        Person savedPerson = personRepo.saveAndFlush(person1);
        Optional<Person> person = personRepo.findById(savedPerson.getPersonId());
        personRepo.deleteById(person.get().getPersonId());
        Optional<Person> deletedPerson = personRepo.findById(savedPerson.getPersonId());
        assertEquals(Optional.empty(), deletedPerson);
    }

    @Test
    public void testFind() {
        Person savedPerson = personRepo.saveAndFlush(person1);
        Optional<Person> person = personRepo.findById(savedPerson.getPersonId());
        assertNotNull(person.get());
    }

    @Test
    public void testFindAll() {
        personRepo.saveAndFlush(person1);
        personRepo.saveAndFlush(person2);
        List<Person> personList = personRepo.findAll();
        assertNotNull(personList);
        assertEquals(2,personList.size());
    }

}
