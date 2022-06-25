package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/films")
public class FilmController {
    
    private Map<Integer, Film> films = new HashMap<>();
    
    @PostMapping("/film")
    public Film addNewFilm(@RequestBody @Valid Film film) {
        validationCheck(film);
        
        films.put(film.getId(), film);
        return film;
    }
    
    @PutMapping("/film/{id}")
    public Film updateFilm(@RequestBody @Valid Film film) {
        validationCheck(film);
        
        films.replace(film.getId(), film);
        return film;
    }
    
    @GetMapping("")
    public Map<Integer, Film> getFilms() {
        return films;
    }
    
    private void validationCheck(Film film) {
        if (film == null) {
            throw new ValidationException();
        }
        if (film.getReleaseDate() == null || film.getReleaseDate()
            .isBefore(LocalDateTime.of(1895, Month.DECEMBER, 28, 00, 00))) {
            
            throw new ValidationException();
        }
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException();
        }
        if (film.getDescription() == null || film.getDescription().length() > 200) {
            throw new ValidationException();
        }
        if (film.getDuration() == null || film.getDuration().isNegative()) {
            throw new ValidationException();
        }
    }
    
}
