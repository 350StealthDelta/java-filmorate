package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDao {
    
    Mpa get(Long id);
    
    List<Mpa> getAllMpas();
}
