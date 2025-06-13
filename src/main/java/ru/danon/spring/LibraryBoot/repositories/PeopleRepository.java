package ru.danon.spring.LibraryBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.danon.spring.LibraryBoot.models.Person;


import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> getPersonByFullName(String fullName);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Person> findWithBooks(@Param("id") int id);
}

