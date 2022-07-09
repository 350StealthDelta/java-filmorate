package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.FilmIdGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;
    
    private FilmIdGenerator idGenerator;
    
    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, FilmIdGenerator idGenerator) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.idGenerator = idGenerator;
    }
    
    /**
     * Добавляет фильм, полученный от {@link ru.yandex.practicum.filmorate.controller.FilmController} в {@link FilmStorage}
     * Перед добавлением назначает фильму id
     * @param film - объект класса Film, который нужно добавить
     */
    public void addFilm(Film film) {
        film.setId(idGenerator.getId());
        filmStorage.save(film);
    }
    
    /**
     * Обновляет данные
     * @param film - объект класса Film, который нужно добавить
     */
    public void updateFilm(Film film) {
            filmStorage.update(film);
    }
    
    /**
     * Удаляет фильм из FilmStorage по id
     * @param id - id фильма, полученный от FilmController
     */
    public void deleteFilm(Long id) {
        filmStorage.delete(id);
    }
    
    /**
     * Возвращает фильм по id
     * @param id - id, полученный из FilmController
     * @return - возвращает фильм из хранилища
     */
    public Film get(Long id) {
        return filmStorage.get(id);
    }
    
    /**
     * Возвращает весь список фильмов из хранилища
     * @return - возвращает список фильмов
     */
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }
    
    /**
     * Добавляет лайк к фильму
     * @param filmId - id фильма, к которому нужно добавить лайк
     * @param userId - id пользователя, который добавляет лайк
     */
    public void addLikeToFilm(Long filmId, Long userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        film.addRate(user);
        filmStorage.update(film);
    }
    
    /**
     * Удаляет лайк у фильма
     * @param filmId - id фильма
     * @param userId - id пользователя, который до этого ставил этот лайк
     */
    public void removeLikeFromFilm(Long filmId, Long userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        film.removeRate(user);
        filmStorage.update(film);
    }
    
    /**
     * Возвращает список самых рейтинговых фильмов
     * @param listSize размер списка возвращаемых фильмов
     * @return - возвращает список фильмов
     */
    public List<Film> getTopRatedFilms(int listSize) {
        return filmStorage
            .getFilms()
            .stream()
            .sorted((o1, o2) -> o2.getRate() - o1.getRate())
            .limit(listSize)
            .collect(Collectors.toList());
    }
    
    //++++++++++++++++
    
    /**
     * Очищает хранилище фильмов и сбрасывает счетчик id
     */
    public void filmsClear() {
        filmStorage.filmsClear();
        idGenerator.idReset();
    }
}
