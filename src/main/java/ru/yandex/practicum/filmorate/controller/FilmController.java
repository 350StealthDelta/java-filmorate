package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotation.OnCreate;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {
    
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;
    
    @PostMapping("")
    @Validated(OnCreate.class)
    public Film addNewFilm(@RequestBody @Valid Film film) {
        nullValidationCheck(film);
        film.setId(idCounter++);
        
        films.put(film.getId(), film);
        log.info(String.format("%s добавлен в коллекцию фильмов с id=%s.", film, film.getId()));
        return film;
    }
    
    @PutMapping("")
    @Validated(OnUpdate.class)
    public Film updateFilm(@RequestBody @Valid Film film) {
        nullValidationCheck(film);
        
        log.info(String.format("Фильм с id=%s обновлен на %s.", film.getId(), film));
        films.replace(film.getId(), film);
        return film;
    }
    
    @GetMapping("")
    public Film[] getFilms() {
        log.info("Возвращен список фильмов.");
        return films.values().toArray(new Film[0]);
    }
    
    //+++++++++++++
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearFilmsMap() {
        log.info("Список фильмов очищен.");
        films.clear();
        idCounter = 1;
        return ResponseEntity.ok().build();
    }
    
    private void nullValidationCheck(Film film) {
        if (film == null) {
            log.warn("Полученный объект film является null.");
            throw new ValidationException("Переданный объект film равен null");
        }
    }
}
