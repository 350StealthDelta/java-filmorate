package ru.yandex.practicum.filmorate.dao.inMemoryImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryFilmDao implements FilmDao {
    
    private final Map<Long, Film> films = new HashMap<>();
    
    @Override
    public void save(Film film) {
        films.put(film.getId(), film);
        log.info(String.format("Добавлен фильм с id=%s.", film.getId()));
    }
    
    @Override
    public void delete(Long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id=%s не найден.", id));
        }
        films.remove(id);
        log.info(String.format("Пользователь с id=%s удален.", id));
    }
    
    @Override
    public void update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id=%s не найден.", film.getId()));
        }
        films.replace(film.getId(), film);
        log.info(String.format("Данные фильма с id=%s обновлены.", film.getId()));
    }
    
    @Override
    public Film get(Long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id=%s не найден.", id));
        }
        Film result = films.get(id);
        log.info(String.format("Найдет и возвращен фильм с id=%s.", id));
        return result;
    }
    
    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }
    
    @Override
    public List<Film> getTopRatedFilms(int listSize) {
        return null;
    }
    
    @Override
    public void addLikeToFilm(Long filmId, Long userId) {
    
    }
    
    @Override
    public void removeLikeFromFilm(Long filmId, Long userId) {
    
    }
    
    //++++++++++++++++
    @Override
    public void filmsClear() {
        films.clear();
    }
    
    @Override
    public boolean isFilmExist(Long id) {
        return false;
    }
}
