package ru.danon.spring.LibraryBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 200, message = "Название должно быть не менее 2 и не более 200 символов")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "ФИО автора не должно быть пустым")
    @Size(min = 2, max = 200, message = "ФИО автора должно быть не менее 2 и не более 200 символов")
    private String author;

    @Column(name = "year")
    @NotNull(message = "Год выпуска книги не должен быть пустым")
    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean taken;

    public Book() {
    }

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isTaken() {
        if (this.takenAt == null) return false;
        long tenDaysInMillis = 10 * 24 * 60 * 60 * 1000L;
        Date now = new Date();

        return now.getTime() - takenAt.getTime() > tenDaysInMillis;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

}
