package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotation.OnCreate;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
@NoArgsConstructor
@Getter
@Setter
public class FilmController {
    private FilmService filmService;
    
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    
    @PostMapping()
    @Validated(OnCreate.class)
    public Film createFilm(@RequestBody @Valid Film film) {
        nullValidationCheck(film);
        
        filmService.addFilm(film);
        log.info(String.format("%s добавлен в коллекцию фильмов с id=%s.", film.getName(), film.getId()));
        return film;
    }
    
    @PutMapping()
    @Validated(OnUpdate.class)
    public Film updateFilm(@RequestBody @Valid Film film) {
        nullValidationCheck(film);
        
        filmService.updateFilm(film);
        log.info(String.format("Фильм с id=%s обновлен на %s.", film.getId(), film));
        return film;
    }
    
    @GetMapping()
    public List<Film> getFilms() {
        List<Film> result = filmService.getFilms();
        log.info("Возвращен список фильмов.");
        return result;
    }
    
    @GetMapping("/popular")
    public List<Film> getPopulat(@PathParam(value = "count") Integer count) {
        if (count == null) {
            count = 10;
        }
        List<Film> result = filmService.getTopRatedFilms(count);
        log.info(String.format("Возвращен список из %s самых популярных фильмов.", count));
        return result;
    }
    
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        Film result = filmService.get(id);
        log.info(String.format("Возвращен фильм с id=%s", id));
        return result;
    }
    
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLikeToFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLikeToFilm(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLikeFromFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLikeFromFilm(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    //+++++++++++++
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearFilmsMap() {
        log.info("Список фильмов очищен.");
        filmService.filmsClear();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    private void nullValidationCheck(Film film) {
        if (film == null) {
            log.warn("Полученный объект film является null.");
            throw new ValidationException("Переданный объект film равен null");
        }
    }
}
