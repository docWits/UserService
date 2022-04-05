package com.romanyuta.UserService.api;

import com.romanyuta.UserService.model.role.dto.RoleRequest;
import com.romanyuta.UserService.model.role.dto.RoleResponse;
import com.romanyuta.UserService.model.user.dto.UserRequest;
import com.romanyuta.UserService.model.user.dto.UserResponse;
import com.romanyuta.UserService.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Роли", description = "Взаимодействие с ролями пользователей ")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @Operation(
            summary = "Список всех ролей",
            description = "Позволяет посмотреть список всех ролей пользователей"
    )
    @GetMapping(path ="/roles" , produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<RoleResponse> findAllRoles(){

        return roleService.findAllRoles();
    }

    @Operation(
            summary = "Регистрация роли",
            description = "Позволяет зарегистрировать роль пользователя"
    )
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleRequest> registerNewUser(@RequestBody RoleRequest roleRequest){
        roleService.addNewRole(roleRequest);
        return ResponseEntity.ok(roleRequest);
    }

    @Operation(
            summary = "Удаление роли",
            description = "Позволяет удалить роль пользователя по id"
    )
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteRole(@PathVariable @Parameter(description = "Идентификатор") Long id){
        roleService.deleteRole(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Изменение роли",
            description = "Позволяет обновить информацию о роли пользователе"
    )
    @PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateRole(
            @PathVariable("id") @Parameter(description = "Идентификатор") Long id,
            @RequestBody RoleRequest roleRequest
    ){
        roleService.updateRole(id,roleRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
