package ru.netology.daohibernatedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.daohibernatedemo.model.Identity;
import ru.netology.daohibernatedemo.model.Person;

import java.util.List;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Identity> {
    List<Person> findAllByCityOfLiving(String cityOfLiving);

    List<Person> findAllByIdentityAgeLessThanOrderByIdentityAge(int age);

    List<Person> findAllByIdentityNameAndIdentitySurname(String name, String surname);
}
