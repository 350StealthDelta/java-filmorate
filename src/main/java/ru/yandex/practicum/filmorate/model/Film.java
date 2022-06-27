package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;
import ru.yandex.practicum.filmorate.annotation.OnCreate;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    
    @NotNull(groups = OnCreate.class)
    @Min(value = 1, groups = OnUpdate.class, message = "id объекта должен быть больше 0")
    private int id;
    
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    
    @Size(max = 200, message = "Размер описания не должен быть больше 200 символов")
    private String description;
    
    @AfterFirstFilm(message = "Дата релиза не может быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    
    @Positive(message = "Длительность должна быть больше нуля")
    @NotNull
    private int duration;
}
