package com.embl.person.controllers;

import com.embl.person.entity.*;
import com.embl.person.model.*;
import com.embl.person.service.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.junit.Test;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(PersonController.class)
@RunWith(SpringRunner.class)
public class PersonControllerWebTest {

    @MockBean
    PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    PersonRequest request1;
    PersonRequest request2;
    Person person1;
    Person person2;

    @Before
    public void setUp() {
        request1 = PersonRequest.builder().firstName("firstName1").lastName("lastName1").age(10).favouriteColour("pink1").build();
        request2 = PersonRequest.builder().firstName("firstName2").lastName("lastName2").age(10).favouriteColour("pink2").build();
        person1 = Person.builder().personId(100L).firstName("firstName1").lastName("lastName1").favouriteColour("pink1").age(10).build();
        person2 = Person.builder().personId(200L).firstName("firstName2").lastName("lastName2").favouriteColour("pink2").age(10).build();
    }

    @Test
    public void test_createPerson() {
        String requestJson = "";
        when(personService.savePerson(request1)).thenReturn(person1);
        try {
            requestJson = mapper.writeValueAsString(request1);
            MvcResult result = this.mockMvc.perform(post("/persons/save").header("loggedInUserName", "userName1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)).andReturn();
            Person personResponse = mapper.readValue(result.getResponse().getContentAsString(), Person.class);
            Assertions.assertEquals("firstName1", personResponse.getFirstName());
            Assertions.assertEquals("lastName1", personResponse.getLastName());
            Assertions.assertEquals("pink1", personResponse.getFavouriteColour());
            Assertions.assertEquals(10, personResponse.getAge());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_createPersons() {
        String requestJson = "";
        when(personService.savePersons(Arrays.asList(request1, request2))).thenReturn(Arrays.asList(person1, person2));
        try {
            requestJson = mapper.writeValueAsString(Arrays.asList(request1, request2));
            MvcResult result = this.mockMvc.perform(post("/persons").header("loggedInUserName", "userName1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)).andReturn();
            List<Person> personListResponse = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(), Person[].class));
            Assertions.assertEquals(2, personListResponse.size());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_getAllPersons() throws Exception {
        when(personService.fetchPersons()).thenReturn(Arrays.asList(person1, person2));
        MvcResult result = this.mockMvc.perform(get("/persons")).andReturn();
        String output = result.getResponse().getContentAsString();
        List<Person> personList = Arrays.asList(mapper.readValue(output, Person[].class));
        Assertions.assertEquals(2, personList.size());
        Assertions.assertEquals("firstName1", personList.get(0).getFirstName());
        Assertions.assertEquals("lastName1", personList.get(0).getLastName());
        Assertions.assertEquals(10, personList.get(1).getAge());
        Assertions.assertEquals("pink2", personList.get(1).getFavouriteColour());
    }

    @Test
    public void test_getPersonById() throws Exception {
        when(personService.getPersonById(100L)).thenReturn(person1);
        MvcResult result = this.mockMvc.perform(get("/persons/{id}", 100L)).andReturn();
        String output = result.getResponse().getContentAsString();
        Person person = mapper.readValue(output, Person.class);
        Assertions.assertEquals("firstName1", person.getFirstName());
        Assertions.assertEquals("lastName1", person.getLastName());
        Assertions.assertEquals(10, person.getAge());
        Assertions.assertEquals("pink1", person.getFavouriteColour());
    }

    @Test
    public void test_deletePerson() {
        doNothing().when(personService).deletePerson(100L);
        try {
            MvcResult result = this.mockMvc.perform(delete("/persons/{id}", 100L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();
            String output = result.getResponse().getContentAsString();
            Assert.assertEquals("Deleted Successfully",output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_updatePerson() {
        when(personService.updatePerson(100L,request1)).thenReturn(person1);
        try {
            String requestJson = mapper.writeValueAsString(request1);
            MvcResult result = this.mockMvc.perform(put("/persons/{id}", 100L)
                    .header("loggedInUserName","userName1")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();

            Person responsePerson = mapper.readValue(result.getResponse().getContentAsString(),Person.class);
            Assert.assertEquals("firstName1",responsePerson.getFirstName());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
