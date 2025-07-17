package com.mosh.course.repositories;

import com.mosh.course.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
