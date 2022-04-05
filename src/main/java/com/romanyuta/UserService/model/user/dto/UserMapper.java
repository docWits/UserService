package com.romanyuta.UserService.model.user.dto;

import com.romanyuta.UserService.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANSE = Mappers.getMapper(UserMapper.class);
    UserResponse mapUserToDto(User user);
    User mapUser(UserRequest userRequest);
}
