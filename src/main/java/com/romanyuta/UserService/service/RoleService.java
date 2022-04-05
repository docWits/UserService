package com.romanyuta.UserService.service;

import com.romanyuta.UserService.exception.ApiRequestException;
import com.romanyuta.UserService.model.role.Role;
import com.romanyuta.UserService.model.role.dto.RoleMapper;
import com.romanyuta.UserService.model.role.dto.RoleRequest;
import com.romanyuta.UserService.model.role.dto.RoleResponse;
import com.romanyuta.UserService.model.user.User;
import com.romanyuta.UserService.model.user.dto.UserMapper;
import com.romanyuta.UserService.model.user.dto.UserRequest;
import com.romanyuta.UserService.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public List<RoleResponse> findAllRoles(){
        return roleRepo.findAll().stream()
                .filter(Objects::nonNull)
                .map(RoleMapper.INSTANSE::mapRoleToDto)
                .collect(Collectors.toList());
    }

    public Role getRoleById(Long id){
        if(!roleRepo.existsById(id)){
            throw new ApiRequestException("role with id=" + id + " does not exist");
        }
        return roleRepo.getById(id);
    }

    public void addNewRole(RoleRequest role){

        if (role != null){
            roleRepo.save(RoleMapper.INSTANSE.mapRole(role));
        }
        else{
            throw new ApiRequestException("cannot add new role");
        }
    }

    public void deleteRole(Long id) {
        if (!roleRepo.existsById(id)) {
            throw new ApiRequestException("role with id=" + id + " does not exist");
        }
        roleRepo.deleteById(id);
    }

    @Transactional
    public void updateRole(Long id, RoleRequest roleRequest) {
        if (!roleRepo.existsById(id)) {
            throw new ApiRequestException("role with id=" + id + " does not exist");
        }
        Role role = RoleMapper.INSTANSE.mapRole(roleRequest);
        role.setId(id);
        roleRepo.saveAndFlush(role);
    }


}
