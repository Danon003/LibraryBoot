package ru.danon.spring.LibraryBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message="ФИО не должно быть пустым")
    @Size(min=2, max=200, message = "Имя должно быть от 2 до 200 символов длиной")
    private String fullName;

    @Column(name = "year_birth")
    @NotNull(message = "Год рождения не должен быть пустым")
    @Temporal(TemporalType.DATE)
    private LocalDate yearBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}
    public Person(String fullName, LocalDate yearBirth) {
        this.fullName = fullName;
        this.yearBirth = yearBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(LocalDate yearBirth) {
        this.yearBirth = yearBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getAge() {
        if (this.yearBirth == null) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        return Period.between(yearBirth, today).getYears();
    }
}
