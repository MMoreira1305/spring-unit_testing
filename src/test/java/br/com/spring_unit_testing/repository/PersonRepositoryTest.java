package br.com.spring_unit_testing.repository;

import br.com.spring_unit_testing.integrationtests.testconteiners.AbstractIntegrationTest;
import br.com.spring_unit_testing.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person0;

    @BeforeEach
    void setup(){
        person0 = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Recife - PE",
                "Male");
    }

    @DisplayName("Given Person Object when save then return saved Person")
    @Test
    void testGIvenPersonObject_WhenSave_thenReturnSavedPerson() {
        // When / Act
        Person savedPerson = personRepository.save(person0);

        //Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("Given Person List when find all then return Person List")
    @Test
    void testGIvenPersonObject_WhenFindAll_thenReturnPersonList() {
        // Given / Arrange

        Person person1 = new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Recife - PE",
                "Male");

        personRepository.save(person0);
        personRepository.save(person1);

        // When / Act
        List<Person> personList = personRepository.findAll();

        //Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("Given Person Object when find by id then return Person Object")
    @Test
    void testGivenPersonObject_WhenFindById_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        Person savedPerson = personRepository.findById(person0.getId()).get();

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when find by email then return Person Object")
    @Test
    void testGivenPersonObject_WhenFindByEmail_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        Person savedPerson = personRepository.findByEmail(person0.getEmail()).get();

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when update person then return updated Person Object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject() {

        // When / Act
        personRepository.save(person0);

        Person savedPerson = personRepository.findByEmail(person0.getEmail()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardo@erudio.com.br");

        Person updatedPerson = personRepository.save(savedPerson);

        //Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }

    @DisplayName("Given Person Object when delete person then remove person")
    @Test
    void testGivenPersonObject_WhenDeletePerson_thenRemovePerson() {

        // When / Act
        personRepository.save(person0);

        personRepository.deleteById(person0.getId());
        Optional<Person> personOptional = personRepository.findById(person0.getId());

        //Then / Assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("Given Person Object when find by JPQL then return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindJPQL_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";
        Person savedPerson = personRepository.findByJPQL(firstName, lastName);

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given Person Object when find by JPQL named Parameters then return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindJPQLNamedParameters_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";
        Person savedPerson = personRepository.findByJPQLNamedParameters(firstName, lastName);

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given Person Object when find by native SQL then return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindByNativeSql_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";
        Person savedPerson = personRepository.findByNativeSQL(firstName, lastName);

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }

    @DisplayName("Given Person Object when find by native SQL with named parameters then return Person Object")
    @Test
    void testGivenFirstNameAndLastName_WhenFindByNativeSqlWithNamedParameters_thenReturnPersonObject() {

        // When / Act
        personRepository.save(person0);

        String firstName = "Leandro";
        String lastName = "Costa";
        Person savedPerson = personRepository.findByNativeSQLWithNamedParameters(firstName, lastName);

        //Then / Assert
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());
    }
}
