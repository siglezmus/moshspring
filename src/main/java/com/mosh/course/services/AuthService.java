package com.mosh.course.services;

import com.mosh.course.models.User;
import com.mosh.course.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = (Long) authentication.getPrincipal();
        return userRepository.findById(id).orElse(null);
    }

    public Long getCurrentUserId(){
        return (Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
