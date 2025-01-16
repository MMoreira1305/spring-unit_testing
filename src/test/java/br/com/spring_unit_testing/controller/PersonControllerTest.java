package br.com.spring_unit_testing.controller;

import br.com.spring_unit_testing.exception.ResourceNotFoundException;
import br.com.spring_unit_testing.model.Person;
import br.com.spring_unit_testing.service.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@SuppressWarnings("ALL")
@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServices service;

    private Person person;

    @BeforeEach
    void setup(){
        person = new Person(
                "matheus",
                "Costa",
                "matheus@erudio.com.br",
                "Recife - PE",
                "Male");
    }

    @DisplayName("Junit test for given Person Object when create Person")
    @Test
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws Exception {
        given(service.create(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("Junit test for given List of Persons when findAll then return Person List")
    @Test
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonList() throws Exception {

        List<Person> persons = service.findAll();
        persons.add(person);
        persons.add(new Person(
                "Joao",
                "Costa",
                "joao@erudio.com.br",
                "Recife - PE",
                "Male"));

        given(service.findAll()).willReturn(persons);

        ResultActions response = mockMvc.perform(get("/person"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));
    }

    @DisplayName("Junit test for given Person Object when find by id then return Person")
    @Test
    void testGivenPersonObject_WhenFindById_thenReturnPersonObject() throws Exception {
        long personId = 1L;

        given(service.findById(personId)).willReturn(person);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("Junit test for given Person invalid id when find by id then return not found")
    @Test
    void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws Exception {
        long personId = 1L;

        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // Cenário Feliz
    @DisplayName("Junit test for given updated Person when update person then return updated person")
    @Test
    void testGivenUpdatedPerson_WhenUpdatePerson_thenReturnPersonUpdated() throws Exception {
        long personId = 1L;

        Person updatedPerson =  new Person(
                "Joao",
                "Costa",
                "joao@erudio.com.br",
                "Recife - PE",
                "Male");

        given(service.findById(personId)).willReturn(person);
        given(service.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    // Cenário Infeliz
    @DisplayName("Junit test for given updated invalid Person when update person then return not found")
    @Test
    void testGivenUpdatedInvalidPerson_WhenUpdatePerson_thenReturnNotFound() throws Exception {
        long personId = 1L;

        Person updatedPerson =  new Person(
                "Joao",
                "Costa",
                "joao@erudio.com.br",
                "Recife - PE",
                "Male");

        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(1));

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        response.
                andExpect(status().isNotFound())
                .andDo(print());

    }

    // Cenário Infeliz
    @DisplayName("Junit test for given person id when delete person then return no content")
    @Test
    void testGivenPersonId_WhenDeletePerson_thenReturnNoContent() throws Exception {
        long personId = 1L;
        willDoNothing().given(service).delete(personId);

        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

        response.
                andExpect(status().isNoContent())
                .andDo(print());

    }
}
