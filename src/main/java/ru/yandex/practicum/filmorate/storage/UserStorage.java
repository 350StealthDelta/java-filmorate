package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    
    void save(User user);
    
    User get(Long id);
    
    void update(User user);
    
    void delete(Long id);
    
    List<User> getUsers();
    
    //++++++++++++++++
    void usersClear();
    
    boolean isUserExist(Long id);
}
