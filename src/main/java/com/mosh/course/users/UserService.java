package com.mosh.course.users;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    public void updateUserPassword(Long id, ChangePasswordRequest request){
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(request.getOldPass())){
            throw new BadCredentialsException("Incorrect password");
        }

        user.setPassword(request.getNewPass());
        userRepository.save(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null) throw new UserNotFoundException();

        userMapper.update(request, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserDto request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyUsedException();
        }
        com.mosh.course.users.User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers(String authToken, String sortBy){
        if(!Set.of("name", "email").contains(sortBy)) sortBy = "name";
        var users = userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
        if (users.isEmpty()) throw new UserNotFoundException();
        return users;
    }


}
