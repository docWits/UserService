package com.romanyuta.UserService.model.role;

import com.romanyuta.UserService.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
//@SequenceGenerator(name = "role_seq", initialValue = 1,allocationSize = 1)
public class Role {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
