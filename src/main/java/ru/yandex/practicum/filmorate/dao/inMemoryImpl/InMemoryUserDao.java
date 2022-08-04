package ru.yandex.practicum.filmorate.dao.inMemoryImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryUserDao implements UserDao {
    
    private final Map<Long, User> users = new HashMap<>();
    
    @Override
    public Long save(User user) {
        users.put(user.getId(), user);
        log.debug(String.format("Добавлен пользователь с id=%s.", user.getId()));
        return 0L;
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
