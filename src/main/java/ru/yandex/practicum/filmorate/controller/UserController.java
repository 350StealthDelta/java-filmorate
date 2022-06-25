package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/users")
public class UserController {
    
    private Map<Integer, User> users = new HashMap<>();
    
    @GetMapping("")
    public Map<Integer, User> getUsers(@RequestBody User user) {
        return users;
    }
    
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        validateUser(user);
    
        users.put(user.getId(), user);
        return user;
    }
    
    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User user) {
        validateUser(user);
        
        users.replace(user.getId(), user);
        return user;
    }
    
    private void validateUser(User user) {
        if (user == null) {
            throw new ValidationException();
        }
        if (user.getId() <= 0) {
            throw new ValidationException();
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()
            || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            
            throw new ValidationException();
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            throw new ValidationException();
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDateTime.now())) {
            throw new ValidationException();
        }
    }
}
