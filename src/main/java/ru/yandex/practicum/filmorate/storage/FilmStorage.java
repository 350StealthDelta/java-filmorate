package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    
    void save(Film film);
    
    void delete(Long id);
    
    void update(Film film);
    
    Film get(Long id);
    
    List<Film> getFilms();
    
    //++++++++++++++++
    void filmsClear();
    
    boolean isFilmExist(Long id);
}
