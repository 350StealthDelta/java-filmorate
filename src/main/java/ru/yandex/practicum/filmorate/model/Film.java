package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    
    @Min(value = 1, groups = OnUpdate.class, message = "id объекта должен быть больше 0")
    private Integer id;
    
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    
    @Size(max = 200, message = "Размер описания не должен быть больше 200 символов")
    private String description;
    
    @AfterFirstFilm(message = "Дата релиза не может быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    
    @Positive(message = "Длительность должна быть больше нуля")
    @NotNull(message = "Duration не может быть null")
    private int duration;
}
