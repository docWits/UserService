package com.romanyuta.UserService.model.role.dto;

import com.romanyuta.UserService.model.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANSE = Mappers.getMapper(RoleMapper.class);
    RoleResponse mapRoleToDto(Role role);
    Role mapRole(RoleRequest roleRequest);


}
