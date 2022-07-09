package ru.yandex.practicum.filmorate.exception;

public class RatedUserNotFound extends RuntimeException{
    public RatedUserNotFound(String message) {
        super(message);
    }
}
