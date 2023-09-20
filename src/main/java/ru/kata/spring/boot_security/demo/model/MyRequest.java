package ru.kata.spring.boot_security.demo.model;


import org.springframework.stereotype.Component;

@Component
public class MyRequest {
    private User user;
    private String roleName;


    public MyRequest() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
