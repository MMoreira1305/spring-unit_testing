package br.com.spring_unit_testing.service;

import br.com.spring_unit_testing.exception.ResourceNotFoundException;
import br.com.spring_unit_testing.model.Person;
import br.com.spring_unit_testing.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    private Person person0;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices services;

    @BeforeEach
    void setup(){
        person0 = new Person(
                "matheus",
                "Costa",
                "matheus@erudio.com.br",
                "Recife - PE",
                "Male");
    }

    @DisplayName("Test given person when save person then return this object")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject(){
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person savedPerson = services.create(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getFirstName(), savedPerson.getFirstName());
    }

    @DisplayName("Test given existing email when save person then throws exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_thenThrowsException(){
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        // Then / Assert
        verify(repository, never()).save(any(Person.class));
    }

    @DisplayName("Test given person list when find all person then return person list")
    @Test
    void testGivenPersonList_WhenFindAllPersons_ThenReturnPersonsList(){
        // Given / Arrange
        Person person1 = new Person(
                "michael",
                "Costa",
                "michael@erudio.com.br",
                "Recife - PE",
                "Male");

        given(repository.findAll()).willReturn(List.of(person0, person1));

        // When / Act
        List<Person> personList = repository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());


    }

    @DisplayName("Test given person list when find all person then return empty person list")
    @Test
    void testGivenPersonList_WhenFindAllPersons_ThenReturnEmptyPersonsList(){
        // Given / Arrange

        given(repository.findAll()).willReturn(Collections.emptyList());

        // When / Act
        List<Person> personList = repository.findAll();

        // Then / Assert
        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());


    }

    @DisplayName("Test given person id when find by id then return this object")
    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject(){
        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        // When / Act
        Person savedPerson = services.findById(1L);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("matheus", savedPerson.getFirstName());
    }

    @DisplayName("Test given person when update then return updated person object")
    @Test
    void testGivenPerson_WhenUpdatePerson_thenReturnUpdatedPersonObject(){
        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        person0.setFirstName("michael");
        person0.setEmail("michael@erudio.com.br");

        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person updatedPerson = services.update(person0);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("michael", updatedPerson.getFirstName());
        assertEquals("michael@erudio.com.br", updatedPerson.getEmail());
    }

    @DisplayName("Test given person when update then return updated person object")
    @Test
    void testGivenPerson_WhenDeletePerson_thenDoNothing(){
        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(repository).delete(person0);

        // When / Act
        services.delete(person0.getId());

        // Then / Assert
        verify(repository, times(1)).delete(person0);
    }

}
