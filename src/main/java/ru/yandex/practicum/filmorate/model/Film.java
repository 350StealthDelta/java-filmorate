package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;
import ru.yandex.practicum.filmorate.exception.RatedUserNotFound;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    
    private Long id;
    
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    
    @Size(max = 200, message = "Размер описания не должен быть больше 200 символов")
    private String description;
    
    @AfterFirstFilm(message = "Дата релиза не может быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    
    @Positive(message = "Длительность должна быть больше нуля")
    @NotNull(message = "Duration не может быть null")
    private int duration;
    
    private int rate;
    
    private Set<Long> ratedUserIds = new HashSet<>();
    
    public void addRate(User user) {
        if (!ratedUserIds.contains(user.getId())) {
            rate++;
            ratedUserIds.add(user.getId());
        }
    }
    
    public void removeRate(User user) {
        if (ratedUserIds.contains(user.getId())) {
            rate--;
            ratedUserIds.remove(user.getId());
        } else {
            throw new RatedUserNotFound(String.format("Пользователь с id=%s не найден в списке ставивших лайк фильму с id=%s",
                user.getId(),
                id));
        }
    }
    
    public Film(Long id,
                String name,
                String description,
                LocalDate releaseDate,
                int duration,
                @Autowired(required = false) int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }
}
