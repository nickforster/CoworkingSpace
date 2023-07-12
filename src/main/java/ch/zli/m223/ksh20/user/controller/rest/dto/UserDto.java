package ch.zli.m223.ksh20.user.controller.rest.dto;

import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.roles.Role;

public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    public UserDto(User user) {
        firstname = user.getFirstname();
        lastname = user.getLastname();
        email = user.getEmail();
    }

    public UserDto() {}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
