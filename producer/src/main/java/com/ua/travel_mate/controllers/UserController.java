package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.Users;
import com.ua.travel_mate.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserController(UserService userService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
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
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {

            Users savedUser = userService.saveUser(users);
            log.info("Sending message to kafka: {}", savedUser.getEmail());
            kafkaTemplate.send("user-created", savedUser.getEmail());
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
