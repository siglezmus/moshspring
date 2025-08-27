package com.mosh.course.auth;

import com.mosh.course.users.User;
import com.mosh.course.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal(); // this is a String ("1")

        Long id = Long.valueOf((String) principal);
        return userRepository.findById(id).orElse(null);
    }

    public Long getCurrentUserId(){
        return (Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
