package ru.netology.daohibernatedemo.service;

import org.springframework.stereotype.Service;
import ru.netology.daohibernatedemo.exception.EmptyResultDataException;
import ru.netology.daohibernatedemo.exception.InvalidCredentials;
import ru.netology.daohibernatedemo.model.Person;
import ru.netology.daohibernatedemo.repository.PersonsRepository;

import java.util.List;

@Service
public class PersonsService {

    PersonsRepository personsRepository;

    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public List<Person> getPersonsByCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new InvalidCredentials("City is empty");
        }
        List<Person> persons = personsRepository.getPersonsByCity(city);
        if (persons.isEmpty()) {
            throw new EmptyResultDataException("Persons are not found");
        } else {
            return persons;
        }
    }
}
