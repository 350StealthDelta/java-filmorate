package ru.yandex.practicum.filmorate.util;

public abstract class FilmorateIdGenerator {
    private Long id;
    
    public FilmorateIdGenerator() {
        this.id = 1L;
    }
    
    public Long getId() {
        return id++;
    }
    
    public void idReset() {
        id = 1L;
    }
}
