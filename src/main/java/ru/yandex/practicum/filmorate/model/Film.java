package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    
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
