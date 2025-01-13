package br.com.spring_unit_testing.service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import br.com.spring_unit_testing.exception.ResourceNotFoundException;
import br.com.spring_unit_testing.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spring_unit_testing.model.Person;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {

        logger.info("Finding all people!");

        return repository.findAll();
    }

    public Person findById(Long id) {

        logger.info("Finding one person!");

        Person person = new Person();
        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAddress("Uberlândia - Minas Gerais - Brasil");
        person.setGender("Male");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this id!"));
    }

    public Person create(Person person) {

        logger.info("Creating one person!");

        return repository.save(person);
    }

    public Person update(Person person) {

        logger.info("Updating one person!");

        var entity = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No record found for this id!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        var entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No record found for this id!"));
        repository.delete(entity);
        logger.info("Deleting one person!");
    }

    private Person mockPerson(int i) {

        Person person = new Person();
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brasil " + i);
        person.setGender("Male");
        return person;
    }
}

