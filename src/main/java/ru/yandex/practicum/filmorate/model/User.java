package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    
    private int id;
    
    @Email(message = "Email должен быть корректным адресом электронной почты")
    @NotNull
    private String email;
    
    @NotBlank(message = "Логин не может состоять из пробелов")
    @NotEmpty(message = "Логин не может быть пустым")
    private String login;
    
    @NotNull
    private String name;
    
    @NotNull
    @Past(message = "Дата рожденья не может быть в будущем или в настоящем")
    private LocalDate birthday;
}
