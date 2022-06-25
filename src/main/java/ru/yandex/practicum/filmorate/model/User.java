package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    
    @Positive(message = "id должно быть больше нуля")
    private int id;
    
    @Email(message = "Email должен быть корректным адресом электронной почты")
    @NotNull
    private String email;
    
    @NotBlank(message = "Логин не может быть пустым")
    @NotEmpty(message = "Логин не может состоять из пробелов")
    @NotNull
    private String login;
    
    @NotNull
    private String name;
    
    @NotNull
    @Past(message = "Дата рожденья не может быть в будущем или в настоящем")
    private LocalDateTime birthday;
}
