package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    
    private static UserController controller;
    private static Validator validator;
    
    private User newUser;
    
    @BeforeAll
    static void beforeAll() {
        controller = new UserController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setLogin("dolore");
        newUser.setName("Nick Name");
        newUser.setEmail("mail@mail.ru");
        newUser.setBirthday(LocalDate.parse("1946-08-20"));
    }
    
    @AfterEach
    void tearDown() {
        controller.clearUserMap();
    }
    
    @Test
    @DisplayName("Проверка работы метода createUser в UserController")
    void createUserTest() {
        assertDoesNotThrow(() -> controller.createUser(newUser));
    }
    
    @Test
    @DisplayName("Проверка корректности email в UserController")
    void emailValidationTest() {
        // Проверка на null
        newUser.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);
        ConstraintViolation<User> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("Email не может быть null", violation.getMessageTemplate());
        
        // Проверка на некорректный email
        newUser.setEmail("НекорректныйЕмейл");
        violations = validator.validate(newUser);
        violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("Email должен быть корректным адресом электронной почты", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности birthday в UserController")
    void birthdayValidationTest() {
        // Проверка на null
        newUser.setBirthday(null);
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);
        ConstraintViolation<User> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("birthday", violation.getPropertyPath().toString());
        assertEquals("Birthday не может быть null", violation.getMessageTemplate());
        
        // Проверка на некорректный birthday
        newUser.setBirthday(LocalDate.now().plusDays(5));
        violations = validator.validate(newUser);
        violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("birthday", violation.getPropertyPath().toString());
        assertEquals("Дата рожденья не может быть в будущем или в настоящем", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректности login в UserController")
    void loginValidationTest() {
        // Проверка на null
        newUser.setLogin(null);
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);
        ConstraintViolation<User> violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("login", violation.getPropertyPath().toString());
        assertEquals("Логин не может состоять из пробелов или быть null", violation.getMessageTemplate());
        
        // Проверка на некорректный login
        newUser.setLogin("   ");
        violations = validator.validate(newUser);
        violation = violations.stream().findFirst()
            .orElseThrow(() -> new ValidationException("Отсутствует ошибка валидации"));
        assertEquals("login", violation.getPropertyPath().toString());
        assertEquals("Логин не может состоять из пробелов или быть null", violation.getMessageTemplate());
    }
    
    @Test
    @DisplayName("Проверка корректной обработки name в UserController")
    void nameValidationTest() {
        // проверка нормальной работы
        controller.createUser(newUser);
        assertEquals("Nick Name", controller.getUsers().get(0).getName());
        
        // проверка на name=null
        controller.clearUserMap();
        newUser.setName(null);
        controller.createUser(newUser);
        assertEquals("dolore", controller.getUsers().get(0).getName());
        
        // проверка на пустое поле name
        controller.clearUserMap();
        newUser.setName("");
        controller.createUser(newUser);
        assertEquals("dolore", controller.getUsers().get(0).getName());
        
        // проверка на поле name, заполненное пробелами
        controller.clearUserMap();
        newUser.setName("   ");
        controller.createUser(newUser);
        assertEquals("dolore", controller.getUsers().get(0).getName());
    }
    
    @Test
    @DisplayName("Проверка корректности обработки случая user=null в UserController")
    void nullValidationTest() {
        newUser = null;
        assertThrows(ValidationException.class, () -> controller.createUser(newUser));
    }
}