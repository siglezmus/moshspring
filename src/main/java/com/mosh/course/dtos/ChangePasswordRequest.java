package com.mosh.course.dtos;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPass;
    private String newPass;
}
