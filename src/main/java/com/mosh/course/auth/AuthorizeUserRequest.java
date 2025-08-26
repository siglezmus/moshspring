package com.mosh.course.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizeUserRequest {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters long")
    private String password;
}
