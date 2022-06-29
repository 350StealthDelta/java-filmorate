package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotation.OnCreate;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {
    
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;
    
    @GetMapping("")
    public User[] getUsers() {
        log.info("Возвращен список пользователей");
        return users.values().toArray(new User[0]);
    }
    
    @PostMapping("")
    @Validated(OnCreate.class)
    public User createUser(@RequestBody @Valid User user) {
        nullUserValidationCheck(user);
        user.setId(idCounter++);
        nameCorrection(user);
        
        users.put(user.getId(), user);
        log.info(String.format("Добавлен пользователь %s с id=%s.", user, user.getId()));
        return user;
    }
    
    @PutMapping("")
    @Validated(OnUpdate.class)
    public User updateUser(@RequestBody @Valid User user) {
        nullUserValidationCheck(user);
        nameCorrection(user);
        
        users.replace(user.getId(), user);
        log.info(String.format("Данные пользователя с id=%s обновлены.", user.getId()));
        return user;
    }
    
    //+++++++++++++++
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearUserMap() {
        log.info("Список пользователей очищен.");
        users.clear();
        idCounter = 1;
        return ResponseEntity.ok().build();
    }
    
    private void nullUserValidationCheck(User user) {
        if (user == null) {
            log.warn("Полученный объект user является null.");
            throw new ValidationException("Полученный объект user является null.");
        }
    }
    
    private void nameCorrection(User user) {
        nullUserValidationCheck(user);
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
