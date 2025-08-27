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

        Object principal = auth.getPrincipal();

        if (principal instanceof JwtUserPrincipal jwtPrincipal) {
            return userRepository.findById(jwtPrincipal.id()).orElse(null);
        }

        // If principal is anonymous or some other type
        return null;
    }

    public Long getCurrentUserId(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        Object principal = auth.getPrincipal();

        if (principal instanceof JwtUserPrincipal jwtPrincipal) {
            return jwtPrincipal.id();
        }
        return null;
    }
}
