package ru.netology.daohibernatedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.daohibernatedemo.model.Identity;
import ru.netology.daohibernatedemo.model.Person;

import java.util.List;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Identity> {

    @Query("select p from Person p where p.cityOfLiving = :city")
    List<Person> findPersonsWithCityOfLiving(@Param("city") String cityOfLiving);

    @Query("select p from Person p where p.identity.age < :age order by p.identity.age")
    List<Person> findPersonsWithAgeLessThanOrderByAge(@Param("age") int age);

    @Query("select p from Person p where p.identity.name = :name and p.identity.surname = :surname")
    List<Person> findPersonsWithNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}
