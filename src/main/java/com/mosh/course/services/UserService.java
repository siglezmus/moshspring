package com.mosh.course.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
    this.notificationService = notificationService;
    this.userRepository = userRepository;
    }

    public void registerUser(User user){
        userRepository.save(user);
        notificationService.send("Hey bro", user.getEmail());
    }
}
