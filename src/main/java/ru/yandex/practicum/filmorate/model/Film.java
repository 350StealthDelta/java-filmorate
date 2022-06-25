package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Film {
    
    @Positive(message = "id должен быть больше нуля")
    private int id;
    
    @NotBlank(message = "Необходимо указать имя")
    @NotNull
    private String name;
    
    @Size(max = 200, message = "Размер описания не должен быть больше 200 символов")
    private String description;
    
    @NotNull
    private LocalDateTime releaseDate;
    
    @Positive(message = "Длительность должна быть больше нуля")
    @NotNull
    private Duration duration;
}
