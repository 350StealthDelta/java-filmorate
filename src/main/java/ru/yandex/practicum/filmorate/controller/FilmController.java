package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    
    //    Logger log = LoggerFactory.getLogger(FilmController.class);
    private Map<Integer, Film> films = new HashMap<>();
    
    @PostMapping("/film")
    public Film addNewFilm(@RequestBody @Valid Film film) {
        validationCheck(film);
        
        films.put(film.getId(), film);
        log.info(String.format("%s добавлен в коллекцию фильмов с id=%s.", film, film.getId()));
        return film;
    }
    
    @PutMapping("/film/{id}")
    public Film updateFilm(@RequestBody @Valid Film film) {
        validationCheck(film);
        
        log.info(String.format("Фильм с id=%s обновлен на %s.", film.getId(), film));
        films.replace(film.getId(), film);
        return film;
    }
    
    @GetMapping("")
    public Map<Integer, Film> getFilms() {
        log.info("Возвращен список фильмов.");
        return films;
    }
    
    private void validationCheck(Film film) {
        if (film == null) {
            log.warn("Полученный объект film является null.");
            throw new ValidationException();
        }
        if (film.getReleaseDate() == null || film.getReleaseDate()
            .isBefore(LocalDateTime.of(1895, Month.DECEMBER, 28, 00, 00))) {
            
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
        if (film.getDuration() == null || film.getDuration().isNegative()) {
            log.warn("Ошибка валидации поля duration объекта film.");
            throw new ValidationException();
        }
    }
    
}
