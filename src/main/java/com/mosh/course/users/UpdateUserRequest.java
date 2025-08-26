package com.mosh.course.users;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
