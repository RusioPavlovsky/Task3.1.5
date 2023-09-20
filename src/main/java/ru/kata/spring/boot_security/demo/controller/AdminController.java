package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyRequest;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/api/users")
    public List<User> getAll () {
        return userService.getAll();
    }


    @PostMapping("/api/user")
    public void addUser(@RequestBody MyRequest request) {
        User user = request.getUser();
        user.setRoles(Arrays.asList(roleService.getByName(request.getRoleName())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @DeleteMapping("/api/user")
    public void deleteUser(@RequestBody Long id) {
        userService.deleteById(id);
    }

    @PatchMapping("api/user")
    public void updateUser (@RequestBody MyRequest request) {
       User user = request.getUser();
       user.setRoles(Arrays.asList(roleService.getByName(request.getRoleName())));
       if (user.getPassword() == null) {
           user.setPassword(userService.getByUsername(user.getUsername()).getPassword());
           userService.update(user);
       } else {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           userService.update(user);
       }
    }

}
