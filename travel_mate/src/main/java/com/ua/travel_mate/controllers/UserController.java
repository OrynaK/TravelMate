package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.Users;
import com.ua.travel_mate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users createUser(@RequestBody Users users) {
        return userService.saveUser(users);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable("id") Integer userId, @RequestBody Users users) {
        if (!userId.equals(users.getId())) {
            throw new IllegalArgumentException("User ID in path must match the ID in the request body");
        }
        return userService.saveUser(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
    }

}
