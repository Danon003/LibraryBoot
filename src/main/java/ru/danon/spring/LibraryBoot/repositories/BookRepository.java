package ru.danon.spring.LibraryBoot.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danon.spring.LibraryBoot.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Override
    Page<Book> findAll(Pageable pageable);
    List<Book> findByNameStartingWith(String name);
}
