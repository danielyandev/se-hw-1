package com.example.sehw1.presentation;

import com.example.sehw1.dto.CreateUserDto;
import com.example.sehw1.dto.UserDto;
import com.example.sehw1.persistence.User;
import com.example.sehw1.presentation.mock.UserRepositoryMock;
import com.example.sehw1.presentation.mock.UserRepositoryMockWithUsersData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.assertj.core.api.Assertions;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
    private UserController userController;

    public UserControllerUnitTest() {
        userController = new UserController(new UserRepositoryMock());
    }
    @Test
     void testCreateUserWithAllData() {
        final CreateUserDto dto = new CreateUserDto();
        dto.setFirstName("Ruben");
        dto.setLastName("Danielyan");
        dto.setEmail("some@email.com");
        dto.setPassword("notsosecretpassword");

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isEqualTo(dto.getFirstName());
        Assertions.assertThat(user.getLastName()).isEqualTo(dto.getLastName());
        Assertions.assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        Assertions.assertThat(user.getError()).isNull();
    }

    @Test
    void testCreateUserWithoutFirstName() {
        final CreateUserDto dto = new CreateUserDto();
        dto.setLastName("Danielyan");
        dto.setEmail("some@email.com");
        dto.setPassword("notsosecretpassword");

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isNull();
        Assertions.assertThat(user.getLastName()).isNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getError()).isEqualTo("First name is required.");
    }

    @Test
    void testCreateUserWithoutLastName() {
        final CreateUserDto dto = new CreateUserDto();
        dto.setFirstName("Ruben");
        dto.setEmail("some@email.com");
        dto.setPassword("notsosecretpassword");

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isNull();
        Assertions.assertThat(user.getLastName()).isNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getError()).isEqualTo("Last name is required.");
    }

    @Test
    void testCreateUserWithoutEmail() {
        final CreateUserDto dto = new CreateUserDto();
        dto.setFirstName("Ruben");
        dto.setLastName("Danielyan");
        dto.setPassword("notsosecretpassword");

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isNull();
        Assertions.assertThat(user.getLastName()).isNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getError()).isEqualTo("Email is required.");
    }

    @Test
    void testCreateUserWithoutPassword() {
        final CreateUserDto dto = new CreateUserDto();
        dto.setFirstName("Ruben");
        dto.setLastName("Danielyan");
        dto.setEmail("some@email.com");

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isNull();
        Assertions.assertThat(user.getLastName()).isNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getError()).isEqualTo("Password is required.");
    }

    @Test
    void testCreateUserWithNoFields() {
        final CreateUserDto dto = new CreateUserDto();

        ResponseEntity<UserDto> result = userController.create(dto);
        UserDto user = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getFirstName()).isNull();
        Assertions.assertThat(user.getLastName()).isNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getError()).isEqualTo(
                "First name is required. Last name is required. Email is required. Password is required."
        );
    }

    @Test
    void testGetAllUsersWhenEmpty() {
        ResponseEntity<List<UserDto>> result = userController.getAll();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isEmpty();
    }

    @Test
    void testGetAllUsersWhenNotEmpty() {
        userController = new UserController(new UserRepositoryMockWithUsersData());
        ResponseEntity<List<UserDto>> result = userController.getAll();
        List<UserDto> list = result.getBody();

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(list).isNotEmpty();

        for (UserDto userDto: list) {
            Assertions.assertThat(userDto.getError()).isNull();
            Assertions.assertThat(userDto.getFirstName()).isNotNull();
            Assertions.assertThat(userDto.getLastName()).isNotNull();
            Assertions.assertThat(userDto.getEmail()).isNotNull();
        }
    }
}
