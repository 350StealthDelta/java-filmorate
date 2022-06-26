package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;
    
    @GetMapping("")
    public User[] getUsers() {
        log.info("Возвращен список пользователей");
        return users.values().toArray(new User[0]);
    }
    
    @PostMapping("")
    public User createUser(@RequestBody User user) {
        if (user != null) {
            user.setId(idCounter++);
        } else {
            throw new ValidationException();
        }
        validateUser(user);
        
        users.put(user.getId(), user);
        log.info(String.format("Добавлен пользователь %s с id=%s.", user, user.getId()));
        return user;
    }
    
    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        validateUser(user);
        
        users.replace(user.getId(), user);
        log.info(String.format("Данные пользователя с id=%s обновлены.", user.getId()));
        return user;
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearUserMap() {
        log.info("Список пользователей очищен.");
        users.clear();
        return ResponseEntity.ok().build();
    }
    
    private void validateUser(User user) {
        if (user == null) {
            log.warn("Полученный объект user является null.");
            throw new ValidationException();
        }
        if (user.getId() <= 0) {
            log.warn("Поле id объекта user должно быть больше нуля.");
            throw new ValidationException();
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()
            || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Ошибка валидации поля email объекта user.");
            throw new ValidationException();
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            log.warn("Ошибка валидации поля login объекта user.");
            throw new ValidationException();
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Поле name объекта user заменено на login.");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации поля birthday объекта user.");
            throw new ValidationException();
        }
    }
}
