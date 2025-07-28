package com.mosh.course.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
}
