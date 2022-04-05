package com.romanyuta.UserService.service;

import com.romanyuta.UserService.exception.ApiRequestException;
import com.romanyuta.UserService.model.role.Role;
import com.romanyuta.UserService.model.user.User;
import com.romanyuta.UserService.model.user.dto.UserMapper;
import com.romanyuta.UserService.model.user.dto.UserRequest;
import com.romanyuta.UserService.model.user.dto.UserResponse;
import com.romanyuta.UserService.repos.RoleRepo;
import com.romanyuta.UserService.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;


    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<UserResponse> findAllUsers(){
        return userRepo.findAll().stream()
                .filter(Objects::nonNull)
                .map(UserMapper.INSTANSE::mapUserToDto)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id){
        if(!userRepo.existsById(id)){
            throw new ApiRequestException("user with id=" + id + " does not exist");
        }
        return userRepo.getById(id);
    }


    public void addNewUser(UserRequest user){

        if (user != null){
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.getById(1L));
            user.setRoles(roles);
            userRepo.save(UserMapper.INSTANSE.mapUser(user));
        }
        else{
            throw new ApiRequestException("cannot add new user");
        }
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ApiRequestException("user with id=" + id + " does not exist");
        }
        userRepo.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, UserRequest userRequest) {
        if (!userRepo.existsById(id)) {
            throw new ApiRequestException("user with id=" + id + " does not exist");
        }
        User user = UserMapper.INSTANSE.mapUser(userRequest);
        user.setId(id);
        userRepo.saveAndFlush(user);
    }


}
