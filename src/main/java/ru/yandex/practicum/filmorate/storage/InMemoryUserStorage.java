package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
@NoArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    
    private final Map<Long, User> users = new HashMap<>();
    
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
        log.debug(String.format("Добавлен пользователь с id=%s.", user.getId()));
    }
    
    @Override
    public void delete(Long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id=%s не найден.", id));
        }
        users.remove(id);
        log.debug(String.format("Пользователь с id=%s удален.", id));
    }
    
    @Override
    public void update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id=%s не найден.", user.getId()));
        }
        users.replace(user.getId(), user);
        log.debug(String.format("Данные пользователя с id=%s обновлены.", user.getId()));
    }
    
    @Override
    public User get(Long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id=%s не найден.", id));
        }
        User result = users.get(id);
        log.debug(String.format("Найдет и возвращен пользователь с id=%s.", id));
        return result;
    }
    
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
    
    //++++++++++++++++
    @Override
    public void usersClear() {
        users.clear();
        log.debug("Список пользователей очищен.");
    }
    
    @Override
    public boolean isUserExist(Long id) {
        if (users.containsKey(id)) {
            return true;
        } else {
            throw new UserNotFoundException(String.format("Пользователь с id=%s не найден.", id));
        }
    }
}
