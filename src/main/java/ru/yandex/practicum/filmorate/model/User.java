package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @NotNull(message = "Email не может быть null")
    private String email;
    
    @NotBlank(message = "Логин не может состоять из пробелов или быть null")
    private String login;
    
    private String name;
    
    @NotNull(message = "Birthday не может быть null")
    @Past(message = "Дата рожденья не может быть в будущем или в настоящем")
    private LocalDate birthday;
}
