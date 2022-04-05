package com.romanyuta.UserService.repos;

import com.romanyuta.UserService.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Role findByName(String name);

}
