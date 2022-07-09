package ru.yandex.practicum.filmorate.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.UserIdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@NoArgsConstructor
public class UserService {
    
    private UserStorage userStorage;
    private UserIdGenerator idGenerator;
    
    @Autowired
    public UserService(UserStorage userStorage, UserIdGenerator idGenerator) {
        this.userStorage = userStorage;
        this.idGenerator = idGenerator;
    }
    
    /**
     * Возвращает пользователя по его id
     *
     * @param id - id пользователя
     * @return - пользователь
     */
    public User getUser(Long id) {
        return userStorage.get(id);
    }
    
    /**
     * Назначает пользователю id и добавляет его в хранилище
     *
     * @param user - пользователь
     */
    public void createUser(User user) {
        user.setId(idGenerator.getId());
        userStorage.save(user);
    }
    
    /**
     * Обновляет данные пользователя
     *
     * @param user - пользователь, которого нужно обновить
     */
    public void updateUser(User user) {
        userStorage.update(user);
    }
    
    /**
     * Удаляет пользователя из хранилища
     *
     * @param id - id пользователя в хранилище
     */
    public void deleteUser(Long id) {
        userStorage.delete(id);
    }
    
    /**
     * Возвращает список пользователей из хранилища
     *
     * @return - список пользователей
     */
    public List<User> getUsers() {
        return userStorage.getUsers();
    }
    
    /**
     * Добавляет пользователей в друзья друг другу
     *
     * @param userId   - id первого пользователя
     * @param friendId - id второго пользователя
     */
    public void friending(Long userId, Long friendId) {
        if (userStorage.isUserExist(userId) && userStorage.isUserExist(friendId)) {
            userStorage.get(userId).setFriendId(friendId);
            userStorage.get(friendId).setFriendId(userId);
        }
    }
    
    /**
     * Удаляет из друзей пользователей друг у друга
     *
     * @param userId   - id первого пользователя
     * @param friendId - id второго пользователя
     */
    public void unfriending(Long userId, Long friendId) {
        if (userStorage.isUserExist(userId) && userStorage.isUserExist(friendId)) {
            userStorage.get(userId).removeFriendIs(friendId);
            userStorage.get(friendId).removeFriendIs(userId);
        }
    }
    
    /**
     * Возвращает список друзей пользователя
     *
     * @param userId - id пользователя
     * @return - список друзей пользователя
     */
    public List<User> getFriends(Long userId) {
        return userStorage
            .getUsers()
            .stream()
            .filter(u -> userStorage.get(userId).getFriendsId().contains(u.getId()))
            .collect(Collectors.toList());
    }
    
    /**
     * Возвращает список общих друзей у пользователей
     *
     * @param firstId  - id первого пользователя
     * @param secondId - id второго пользователя
     * @return - список общих друзей пользователей
     */
    public List<User> getCommonFriends(Long firstId, Long secondId) {
        Set<Long> firstUserFriends = userStorage.get(firstId).getFriendsId();
        Set<Long> secondUserFriends = userStorage.get(secondId).getFriendsId();
        if (firstUserFriends == null || secondUserFriends == null) {
            return new ArrayList<>();
        }
        
        Set<Long> commons = firstUserFriends.stream()
            .filter(secondUserFriends::contains)
            .collect(Collectors.toSet());
        
        return userStorage.getUsers()
            .stream()
            .filter(user -> commons.contains(user.getId()))
            .collect(Collectors.toList());
    }
    
    //++++++++++++++++++
    
    /**
     * Очищает хранилище пользователей и сбрасывает счетчик id
     */
    public void usersClear() {
        userStorage.usersClear();
        idGenerator.idReset();
    }
}
