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

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        var principal = (JwtUserPrincipal) auth.getPrincipal();

        return userRepository.findById(principal.id()).orElse(null);

    }

    public Long getCurrentUserId(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (JwtUserPrincipal) auth.getPrincipal();
        return principal.id();

    }
}
