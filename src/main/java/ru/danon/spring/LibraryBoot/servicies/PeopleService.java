package ru.danon.spring.LibraryBoot.servicies;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danon.spring.LibraryBoot.models.Person;
import ru.danon.spring.LibraryBoot.repositories.PeopleRepository;
import ru.danon.spring.LibraryBoot.models.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findWithBooks(id).orElse(null);
    }

    @Transactional
    public void save(@Valid Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return peopleRepository.findWithBooks(id)
                .map(Person::getBooks)
                .orElse(Collections.emptyList());
    }


    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.getPersonByFullName(fullName);
    }
}
