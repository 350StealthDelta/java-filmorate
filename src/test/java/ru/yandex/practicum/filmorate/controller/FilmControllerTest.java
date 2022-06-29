package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    
    private static FilmController controller;
    Film newFilm;
    
    @BeforeAll
    static void beforeAll() {
        controller = new FilmController();
    }
    
    @BeforeEach
    void setUp() {
        newFilm = new Film();
        newFilm.setName("nisi eiusmod");
        newFilm.setDescription("adipisicing");
        newFilm.setReleaseDate(LocalDate.of(1967, 3, 25));
        newFilm.setDuration(100);
    }
    
    @AfterEach
    void tearDown() {
        controller.clearFilmsMap();
    }
    
    @Test
    void filmCreate() {
        assertDoesNotThrow(() -> controller.addNewFilm(newFilm), "Ошибка добавления корректного фильма");
    }
    
    @Test
    @DisplayName("Если поле name пустое, то возвращается код 400")
    void filmValidationNameTest() {
        newFilm.setName("");
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("Необходимо указать имя", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Если description больше 200 символов, то возвращается код 400")
    void filmValidationDescriptionTest() {
        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
            "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, " +
            "который за время «своего отсутствия», стал кандидатом Коломбани.");
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("description", violation.getPropertyPath().toString());
        assertEquals("Размер описания не должен быть больше 200 символов", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Если releaseDate раньше 28 декабря 1895 года, то возвращается код 400")
    void filmValidationReleaseDateTest() {
        newFilm.setReleaseDate(LocalDate.of(1890, 3, 25));
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("releaseDate", violation.getPropertyPath().toString());
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Если duration меньше нуля, то возвращается код 400")
    void filmValidationDurationTest() {
        newFilm.setDuration(-200);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("duration", violation.getPropertyPath().toString());
        assertEquals("Длительность должна быть больше нуля", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("")
    void filmValidationIdTest() {
        newFilm.setId(-5);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm, OnUpdate.class);
        
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
        assertEquals("id", violation.getPropertyPath().toString());
        assertEquals("id объекта должен быть больше 0", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Если film = null, то выбрасывается исключение ValidationException")
    void filmNullValidationTest() {
        newFilm = null;
        
        assertThrows(ValidationException.class, () -> controller.addNewFilm(newFilm)
            , "Ошибка проверки film на null");
    }
}