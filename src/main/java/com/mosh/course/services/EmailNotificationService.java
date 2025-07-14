package com.mosh.course.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("Email")
@Primary
public class EmailNotificationService implements NotificationService{
    @Value("${notification.email.provider}")
    private String notificationServiceProvider;

    @Value("${notification.email.port}")
    private String notificationPort;

    @Override
    public void send(String message, String destination) {
        System.out.println("Email");
        System.out.println("To: " + destination);
        System.out.println("Sent by: " + notificationServiceProvider + "though port: " + notificationPort );
        System.out.println(message);
    }
}
