package com.ua.travel_mate.services;

import com.ua.travel_mate.entities.Users;
import com.ua.travel_mate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Users saveUser(Users users) {
        return userRepository.save(users);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

}
