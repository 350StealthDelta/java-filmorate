package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.util.FilmIdGenerator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    
    private static FilmController controller;
    private static Validator validator;
    private Film newFilm;
    
    @BeforeAll
    static void beforeAll() {
        controller = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage(), new FilmIdGenerator()));
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @BeforeEach
    void setUp() {
        newFilm = new Film();
        newFilm.setName("nisi eiusmod");
        newFilm.setDescription("adipisicing");
        newFilm.setReleaseDate(LocalDate.parse("1967-03-25"));
        newFilm.setDuration(100);
    }
    
    @AfterEach
    void tearDown() {
        controller.clearFilmsMap();
    }
    
    @Test
    @DisplayName("Проверка работы метода addNewFilm в FilmController")
    void filmCreateTest() {
        assertDoesNotThrow(() -> controller.createFilm(newFilm), "Ошибка добавления корректного фильма");
    }
    
    @Test
    @DisplayName("Проверка корректности валидации name в FilmController")
    void nameValidationTest() {
        newFilm.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("Необходимо указать имя", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности валидации description в FilmController")
    void descriptionValidationTest() {
        newFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
            "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, " +
            "который за время «своего отсутствия», стал кандидатом Коломбани.");
        
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("description", violation.getPropertyPath().toString());
        assertEquals("Размер описания не должен быть больше 200 символов", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности валидации releaseDate в FilmController")
    void releaseDateValidationTest() {
        newFilm.setReleaseDate(LocalDate.of(1890, 3, 25));
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("releaseDate", violation.getPropertyPath().toString());
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности валидации duration в FilmController")
    void durationValidationTest() {
        newFilm.setDuration(-200);
        Set<ConstraintViolation<Film>> violations = validator.validate(newFilm);
        ConstraintViolation<Film> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("duration", violation.getPropertyPath().toString());
        assertEquals("Длительность должна быть больше нуля", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности обработки случая film=null в FilmController")
    void filmNullValidationTest() {
        newFilm = null;
        
        assertThrows(ValidationException.class, () -> controller.createFilm(newFilm)
            , "Ошибка проверки film на null");
    }
}