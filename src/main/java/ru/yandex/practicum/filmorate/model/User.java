package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User {
    
    private Long id;
    
    @Email(message = "Email должен быть корректным адресом электронной почты")
    @NotNull(message = "Email не может быть null")
    private String email;
    
    @NotBlank(message = "Логин не может состоять из пробелов или быть null")
    private String login;
    
    private String name;
    
    @NotNull(message = "Birthday не может быть null")
    @Past(message = "Дата рожденья не может быть в будущем или в настоящем")
    private LocalDate birthday;
    
    private Set<Long> friendsId = new HashSet<>();
    
    public void setFriendId(Long id) {
        friendsId.add(id);
    }
    
    public void removeFriendIs(Long id) {
        friendsId.remove(id);
    }
    
    public User(Long id,
                String email,
                String login,
                String name,
                LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
