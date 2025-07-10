package com.mosh.course.services;

import org.springframework.stereotype.Service;

@Service("SMS")
public class SMSNotificationService implements NotificationService{
    @Override
    public void send(String message) {
        System.out.println("SMS");
        System.out.println(message);
    }
}
