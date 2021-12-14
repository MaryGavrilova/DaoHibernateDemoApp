package ru.netology.daohibernatedemo.service;

import org.springframework.stereotype.Service;
import ru.netology.daohibernatedemo.exception.EmptyResultDataException;
import ru.netology.daohibernatedemo.exception.InvalidCredentials;
import ru.netology.daohibernatedemo.model.Identity;
import ru.netology.daohibernatedemo.model.Person;
import ru.netology.daohibernatedemo.repository.PersonsRepository;

import java.util.List;

@Service
public class PersonsService {

    PersonsRepository personsRepository;

    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public Person createPerson(Person person) {
        if (personsRepository.existsById(person.getIdentity())) {
            throw new InvalidCredentials("Person with this name, surname and age already exists");
        } else {
            return personsRepository.save(person);
        }
    }

    public List<Person> readAllPersons() {
        List<Person> persons = personsRepository.findAll();
        if (persons.isEmpty()) {
            throw new EmptyResultDataException("Persons are not found");
        } else {
            return persons;
        }
    }

    public Person readPerson(String name, String surname, int age) {
        return personsRepository.findById(new Identity(name, surname, age)).orElseThrow(() ->
                new EmptyResultDataException("Person is not found"));
    }

    public Person updatePerson(Person person) {
        if (personsRepository.existsById(person.getIdentity())) {
            return personsRepository.save(person);
        } else {
            throw new EmptyResultDataException("Person is not found");
        }
    }

    public void deletePerson(String name, String surname, int age) {
        Identity identity = new Identity(name, surname, age);
        if (personsRepository.existsById(identity)) {
            personsRepository.deleteById(identity);
        } else {
            throw new EmptyResultDataException("Person is not found");
        }
    }

    public List<Person> findAllByCityOfLiving(String city) {
        List<Person> persons = personsRepository.findAllByCityOfLiving(city);
        if (persons.isEmpty()) {
            throw new EmptyResultDataException("Persons are not found");
        } else {
            return persons;
        }
    }

    public List<Person> findAllByIdentityAgeLessThanOrderByAge(int age) {
        List<Person> persons = personsRepository.findAllByIdentityAgeLessThanOrderByIdentityAge(age);
        if (persons.isEmpty()) {
            throw new EmptyResultDataException("Persons are not found");
        } else {
            return persons;
        }
    }

    public List<Person> findAllByIdentityNameAndSurname(String name, String surname) {
        List<Person> persons = personsRepository.findAllByIdentityNameAndIdentitySurname(name, surname);
        if (persons.isEmpty()) {
            throw new EmptyResultDataException("Persons are not found");
        } else {
            return persons;
        }
    }
}
