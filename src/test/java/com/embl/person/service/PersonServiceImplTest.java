package com.embl.person.service;

import com.embl.person.entity.*;
import com.embl.person.excpetion.*;
import com.embl.person.model.*;
import com.embl.person.repo.*;
import com.fasterxml.jackson.databind.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonServiceImplTest {
    @Mock
    PersonRepo personRepo;

    @Mock
    ObjectMapper objectMapper;


    @InjectMocks
    PersonServiceImpl personService = spy(new PersonServiceImpl());

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
    public void test_savePerson() {
        when(objectMapper.convertValue(request1, Person.class)).thenReturn(person1);
        personService.savePerson(request1);
        verify(personRepo, times(1)).save(any());
    }

    @Test
    public void test_savePersons() {
        personService.savePersons(Arrays.asList(request1, request2));
        verify(personRepo, times(1)).saveAll(anyList());
    }

    @Test
    public void updatePerson() {
        when(personRepo.findById(100L)).thenReturn(Optional.of(person1));
        when(objectMapper.convertValue(request1, Person.class)).thenReturn(person1);
        personService.updatePerson(100L, request1);
        verify(personRepo, times(1)).save(any(Person.class));
    }

    @Test(expected = PersonNotFoundException.class)
    public void updatePerson_exception() {
        when(personRepo.findById(100L)).thenThrow(new PersonNotFoundException("Person Not available"));
        personService.updatePerson(100L, request1);
    }

    @Test
    public void updatePerson_exception_Message() {
        when(personRepo.findById(100L)).thenThrow(new PersonNotFoundException("Person Not available"));
        assertThatThrownBy(() -> personService.updatePerson(100L, request1)).isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("Person Not available");
    }

    @Test
    public void deletePerson() {
        personService.deletePerson(100L);
        verify(personRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void fetchPersons() {
        when(personRepo.findAll()).thenReturn(Arrays.asList(person1, person2));
        List<Person> personList = personService.fetchPersons();
        assertThat(personList);
        Assert.assertEquals(2, personList.size());
        Assert.assertEquals("firstName1", personList.get(0).getFirstName());
        Assert.assertEquals("lastName1", personList.get(0).getLastName());
        Assert.assertEquals("pink1", personList.get(0).getFavouriteColour());
        Assert.assertEquals(10, personList.get(0).getAge().intValue());
        Assert.assertEquals("firstName2", personList.get(1).getFirstName());
        Assert.assertEquals("lastName2", personList.get(1).getLastName());
        Assert.assertEquals("pink2", personList.get(1).getFavouriteColour());
        Assert.assertEquals(10, personList.get(1).getAge().intValue());

    }

    @Test
    public void test_getPersonById() {
        when(personRepo.findById(100L)).thenReturn(Optional.of(person1));
        Person person = personService.getPersonById(100L);
        Assert.assertEquals(person1.getFirstName(),person.getFirstName());
        Assert.assertEquals(person1.getLastName(),person.getLastName());
        Assert.assertEquals(person1.getAge(),person.getAge());
        Assert.assertEquals(person1.getFavouriteColour(),person.getFavouriteColour());
    }

    @Test(expected = PersonNotFoundException.class)
    public void test_getPersonById_exception() {
        when(personRepo.findById(100L)).thenThrow(new PersonNotFoundException("No Person available with this id"));
        personService.getPersonById(100L);
    }

    @Test
    public void test_getPersonById_exception_message() {
        when(personRepo.findById(100L)).thenThrow(new PersonNotFoundException("No Person available with this id"));
        assertThatThrownBy(()->personService.getPersonById(100L)).isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("No Person available with this id");
    }



}
