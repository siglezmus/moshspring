package com.mosh.course.mappers;

import com.mosh.course.dtos.UserDto;
import com.mosh.course.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
