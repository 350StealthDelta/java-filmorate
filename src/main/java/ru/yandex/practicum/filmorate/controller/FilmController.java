package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    
    //    Logger log = LoggerFactory.getLogger(FilmController.class);
    private Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;
    
    @PostMapping("")
    public Film addNewFilm(@RequestBody @Valid Film film) {
        if (film != null) {
            film.setId(idCounter++);
        } else {
            throw new ValidationException();
        }
        validationCheck(film);
        
        films.put(film.getId(), film);
        log.info(String.format("%s добавлен в коллекцию фильмов с id=%s.", film, film.getId()));
        return film;
    }
    
    @PutMapping("")
    public Film updateFilm(@RequestBody @Valid Film film) {
        validationCheck(film);
        
        
        log.info(String.format("Фильм с id=%s обновлен на %s.", film.getId(), film));
        films.replace(film.getId(), film);
        return film;
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearFilmsMap() {
        log.info("Список фильмов очищен.");
        films.clear();
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("")
    public Film[] getFilms() {
        log.info("Возвращен список фильмов.");
        return films.values().toArray(new Film[0]);
    }
    
    private void validationCheck(Film film) {
        if (film == null) {
            log.warn("Полученный объект film является null.");
            throw new ValidationException();
        }
        if (film.getId() <= 0) {
            log.warn("Поле id объекта user должно быть больше нуля.");
            throw new ValidationException();
        }
        if (film.getReleaseDate() == null || film.getReleaseDate()
            .isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            
            log.warn("Ошибка валидации поля releaseDate объекта film.");
            throw new ValidationException();
        }
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("Ошибка валидации поля name объекта film.");
            throw new ValidationException();
        }
        if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("Ошибка валидации размера поля description объекта film.");
            throw new ValidationException();
        }
        if (film.getDuration() < 0) {
            log.warn("Ошибка валидации поля duration объекта film.");
            throw new ValidationException();
        }
    }
    
}
