package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.OnUpdate;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @Min(value = 1, groups = OnUpdate.class, message = "id объекта должен быть больше 0")
    private Integer id;
    
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
