package com.mosh.course.services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class InMemoryUserRepository implements UserRepository {
    @Override
    public void save(User user) {
        System.out.println("Saved user:" + user.toString());
    }
}
