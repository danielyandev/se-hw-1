package com.example.sehw1.presentation;

import com.example.sehw1.dto.CreateUserDto;
import com.example.sehw1.dto.UserDto;
import com.example.sehw1.persistence.User;
import com.example.sehw1.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create new user
     *
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserDto dto) {
        String error = validate(dto);
        final UserDto responseDto = new UserDto();

        if (error.length() > 0) {
            responseDto.setError(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        // validation passed

        final User user = new User();
        user.fill(dto);

        final User createdUser = userRepository.save(user);
        responseDto.fill(createdUser);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Check if all fields are not null
     *
     * @param dto
     * @return
     */
    private String validate(CreateUserDto dto) {
        String message = "";
        String delimiter = "";

        if (dto.getFirstName() == null) {
            message += "First name is required.";
            delimiter = " ";
        }

        if (dto.getLastName() == null) {
            message += delimiter + "Last name is required.";
            delimiter = " ";
        }

        if (dto.getEmail() == null) {
            message += delimiter + "Email is required.";
            delimiter = " ";
        }

        if (dto.getPassword() == null) {
            message += delimiter + "Password is required.";
        }

        return message;
    }
}
