package com.mosh.course.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "favoriteProducts", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(RegisterUserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "favoriteProducts", ignore = true)
    @Mapping(target = "role", ignore = true)
    void update(UpdateUserRequest request, @MappingTarget User user);
}
