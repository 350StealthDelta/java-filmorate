package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    
    Genre getGenre(Long id);
    
    List<Genre> getAllGenres();
}
