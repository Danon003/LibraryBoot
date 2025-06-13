package ru.danon.spring.LibraryBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danon.spring.LibraryBoot.servicies.PeopleService;
import ru.danon.spring.LibraryBoot.models.Person;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleService.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");



        if (person.getYearBirth() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(person.getYearBirth());
                LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                errors.rejectValue("yearBirth", "", "Неверный формат даты. Используй dd/MM/yyyy");
            }

            try {
                LocalDate birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(person.getYearBirth().toString()).toInstant()
                        .atZone(ZoneId.of("UTC")).toLocalDate();

                LocalDate now = LocalDate.now();

                if (birthDate.isAfter(now)) {
                    errors.rejectValue("yearBirth", "", "Дата рождения не может быть в будущем");
                } else if (Period.between(birthDate, now).getYears() < 0) {
                    errors.rejectValue("yearBirth", "", "Возраст должен быть от 0 и выше");
                }

            } catch (Exception e) {
                // Если не получилось спарсить — пропускаем, ошибка уже добавлена выше
            }

        } else {
            errors.rejectValue("yearBirth", "", "Неверный формат даты. Используй dd/MM/yyyy");
        }
    }
}

