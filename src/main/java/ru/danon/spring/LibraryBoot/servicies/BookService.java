package ru.danon.spring.LibraryBoot.servicies;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danon.spring.LibraryBoot.models.Book;
import ru.danon.spring.LibraryBoot.repositories.BookRepository;
import ru.danon.spring.LibraryBoot.repositories.PeopleRepository;
import ru.danon.spring.LibraryBoot.models.Person;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }


    public List<Book> findAll(boolean findByYear) {
        if (!findByYear)
            return bookRepository.findAll(Sort.by("name"));
        else
            return bookRepository.findAll(Sort.by("year"));
    }


    public List<Book> findWithPagination(Integer page, Integer bookPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, bookPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, bookPerPage)).getContent();

    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void update(int id, @Valid Book book) {
        book.setId(id);
        book.setOwner(bookRepository.findById(id).get().getOwner());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Hibernate.initialize(book.get().getOwner());
            return Optional.ofNullable(book.get().getOwner());
        }else {
            return Optional.empty();
        }

    }

    @Transactional
    public void release(int id) {
       bookRepository.findById(id).ifPresent(
               book -> {
                   book.setOwner(null);
                   book.setTakenAt(null);
               }
       );
    }

    @Transactional
    public void assign(int id, Person person) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTakenAt(new Date());
                }
        );
    }

    public List<Book> showSearchBooks(String search) {
        return bookRepository.findByNameStartingWith(search);
    }

}
