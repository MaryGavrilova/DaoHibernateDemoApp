package ru.netology.daohibernatedemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.daohibernatedemo.exception.EmptyResultDataException;
import ru.netology.daohibernatedemo.exception.InvalidCredentials;
import ru.netology.daohibernatedemo.model.Identity;
import ru.netology.daohibernatedemo.model.Person;
import ru.netology.daohibernatedemo.service.PersonsService;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
public class PersonsController {

    PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    //CRUD операции
    @PostMapping("/persons/create")
    @RolesAllowed("ROLE_WRITE")
    public ResponseEntity<String> createPerson(@Valid @RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.OK).body(personsService.createPerson(person) + " created");
    }

    @GetMapping("/persons/read-all")
    @Secured("ROLE_READ")
    public List<Person> readAllPersons() {
        return personsService.readAllPersons();
    }

    @GetMapping("/persons/read-one")
    @Secured("ROLE_READ")
    public Person readPerson(@RequestParam("name") @NotBlank String name,
                             @RequestParam("surname") @NotBlank String surname,
                             @RequestParam("age") @Min(0) Integer age) {
        return personsService.readPerson(name, surname, age);
    }

    @PostMapping("/persons/update")
    @RolesAllowed("ROLE_WRITE")
    public ResponseEntity<String> updatePerson(@Valid @RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.OK).body(personsService.updatePerson(person) + " updated");
    }

    @DeleteMapping("/persons/delete")
    @PreAuthorize("hasRole('DELETE')")
    public ResponseEntity<String> deletePerson(@RequestParam("name") @NotBlank String name,
                                               @RequestParam("surname") @NotBlank String surname,
                                               @RequestParam("age") @Min(0) Integer age) {
        personsService.deletePerson(name, surname, age);
        return ResponseEntity.status(HttpStatus.OK).body("Person with " + new Identity(name, surname, age) + " deleted");
    }

    //Дополнительные методы
    @GetMapping("/persons/by-city")
    @PreAuthorize("hasRole('DELETE') or hasRole('WRITE')")
    public List<Person> findAllByCityOfLiving(@RequestParam("city") @NotBlank String city) {
        return personsService.findAllByCityOfLiving(city);
    }

    @GetMapping("/persons/by-age-less-than")
    @RolesAllowed("ROLE_WRITE")
    public List<Person> findAllByIdentityAgeLessThanOrderByAge(@RequestParam("age") @Min(0) Integer age) {
        return personsService.findAllByIdentityAgeLessThanOrderByAge(age);
    }

    @GetMapping("/persons/by-name-and-surname")
    @PreAuthorize("authentication.principal.username == #name")
    public List<Person> findAllByIdentityNameAndSurname(@RequestParam("name") @NotBlank String name,
                                                        @RequestParam("surname") @NotBlank String surname) {
        return personsService.findAllByIdentityNameAndSurname(name, surname);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataException.class)
    ResponseEntity<String> handleEmptyResultDataException(EmptyResultDataException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCredentials.class)
    ResponseEntity<String> handleInvalidCredentials(InvalidCredentials e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
