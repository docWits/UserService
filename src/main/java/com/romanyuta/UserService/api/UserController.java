package com.romanyuta.UserService.api;

import com.romanyuta.UserService.model.user.dto.UserRequest;
import com.romanyuta.UserService.model.user.dto.UserResponse;
import com.romanyuta.UserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями ")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @Operation(
            summary = "Список всех пользователей",
            description = "Позволяет посмотреть список всех пользователей"
    )
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<UserResponse> findAllUsers(){

        return userService.findAllUsers();
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегестрировать пользователя"
    )
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserRequest> registerNewUser(@RequestBody UserRequest userRequest){
        userService.addNewUser(userRequest);
        return ResponseEntity.ok(userRequest);
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя по id"
    )
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable @Parameter(description = "Идентификатор") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Изменение пользователей",
            description = "Позволяет обновить информацию о пользователе"
    )
    @PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateUser(
            @PathVariable("id") @Parameter(description = "Идентификатор") Long id,
            @RequestBody UserRequest userRequest
    ){
        userService.updateUser(id,userRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
