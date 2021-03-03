package com.embl.person.controllers;

import com.embl.person.*;
import com.embl.person.entity.*;
import com.embl.person.model.*;
import org.junit.Test;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.boot.web.server.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

@SpringBootTest(classes = PersonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Profile("test")
public class PersonControllerIntegrationTest {

    @LocalServerPort
    private int port;

    PersonRequest request1;
    PersonRequest request2;
    Person person1;
    Person person2;
    Long id = 100L;
    TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setUp() {
        request1 = PersonRequest.builder().firstName("firstName1").lastName("lastName1").age(10).favouriteColour("pink1").build();
        request2 = PersonRequest.builder().firstName("firstName2").lastName("lastName2").age(10).favouriteColour("pink2").build();
        person1 = Person.builder().personId(100L).firstName("firstName1").lastName("lastName1").favouriteColour("pink1").age(10).build();
        person2 = Person.builder().personId(200L).firstName("firstName2").lastName("lastName2").favouriteColour("pink2").age(10).build();
    }

    @Test
    public void test_createPerson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("loggedInUserName", "userName1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PersonRequest> request = new HttpEntity<>(request1, headers);
        Person personResponse = this.restTemplate.postForObject("http://localhost:" + port + "/embl/persons/save", request, Person.class);
        Assert.assertEquals("firstName1", personResponse.getFirstName());
        Assertions.assertEquals("lastName1", personResponse.getLastName());
        Assertions.assertEquals("pink1", personResponse.getFavouriteColour());
        Assertions.assertEquals(10, personResponse.getAge());
    }

    @Test
    public void test_createPersons() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("loggedInUserName", "userName1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<PersonRequest>> request = new HttpEntity<>(Arrays.asList(request1, request2), headers);
        Person[] personResponse = this.restTemplate.postForObject("http://localhost:" + port + "/embl/persons", request, Person[].class);
        Assert.assertEquals("firstName1", personResponse[0].getFirstName());
        Assert.assertEquals("lastName1", personResponse[0].getLastName());
        Assert.assertEquals("pink1", personResponse[0].getFavouriteColour());
        Assert.assertEquals(10, personResponse[0].getAge().intValue());
        Assert.assertEquals("firstName2", personResponse[1].getFirstName());
        Assert.assertEquals("lastName2", personResponse[1].getLastName());
        Assert.assertEquals("pink2", personResponse[1].getFavouriteColour());
        Assert.assertEquals(10, personResponse[1].getAge().intValue());
    }

    @Test
    public void test_getAllPersons() {
        Person[] persons = this.restTemplate.getForObject("http://localhost:" + port + "/embl/persons", Person[].class);
        Assert.assertNotNull(persons);
    }

    @Test
    public void test_getPersonById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        Person person = this.restTemplate.getForObject("http://localhost:" + port + "/embl/persons/{id}", Person.class,params);
        Assert.assertNotNull(person);
    }

    @Test
    public void test_deletePerson() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        this.restTemplate.delete("http://localhost:" + port + "/embl/persons/",params);
    }

    @Test
    public void test_updatePerson() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("loggedInUserName", "userName1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PersonRequest> request = new HttpEntity<>(request2,headers);
        this.restTemplate.put("http://localhost:" + port + "/embl/persons/{id}",request,headers,params);
    }

}
