package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    
    private Map<Integer, User> users = new HashMap<>();
    
    @GetMapping("")
    public Map<Integer, User> getUsers(@RequestBody User user) {
        log.info("Возвращен список пользователей");
        return users;
    }
    
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        validateUser(user);
        
        users.put(user.getId(), user);
        log.info(String.format("Добавлен пользователь %s с id=%s.", user, user.getId()));
        return user;
    }
    
    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User user) {
        validateUser(user);
        
        users.replace(user.getId(), user);
        log.info(String.format("Данные пользователя с id=%s обновлены."));
        return user;
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
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDateTime.now())) {
            log.warn("Ошибка валидации поля birthday объекта user.");
            throw new ValidationException();
        }
    }
}
