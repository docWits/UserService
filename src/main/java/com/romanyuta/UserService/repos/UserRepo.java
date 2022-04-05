package com.romanyuta.UserService.repos;

import com.romanyuta.UserService.model.role.Role;
import com.romanyuta.UserService.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByLogin(String login);


}
