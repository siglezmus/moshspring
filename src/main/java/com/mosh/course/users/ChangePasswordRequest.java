package com.mosh.course.users;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPass;
    private String newPass;
}
