package com.ua.travel_mate.testData;

import com.ua.travel_mate.entities.Users;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.time.LocalDateTime;

public class UsersTestData {
    public static Users getUserData() {
        return Users.builder().username("john_doe").email("john.doe@example.com").passwordHash("hashed_password")
                .firstname("John").lastname("Doe").profilePicture("profile.jpg").bio("Software Developer").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public static Users getJaneUser() {
        return Users.builder().username("jane_doe").email("jane.doe@example.com").passwordHash("hashed_password")
                .firstname("Jane").lastname("Doe").profilePicture("profile.jpg").bio("Product Manager").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public static Users.UsersBuilder getUser() {
        return Users.builder().username("jane_doe").email("jane.doe@example.com").passwordHash("hashed_password")
                .firstname("Jane").lastname("Doe").profilePicture("profile.jpg")
                .bio("Product Manager").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build().toBuilder();
    }
}
