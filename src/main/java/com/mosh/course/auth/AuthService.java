package com.mosh.course.auth;

import com.mosh.course.users.User;
import com.mosh.course.users.UserRepository;
import lombok.AllArgsConstructor;
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
