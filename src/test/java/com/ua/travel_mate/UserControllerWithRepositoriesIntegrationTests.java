package com.ua.travel_mate;

import com.ua.travel_mate.containers.MySQLTestContainer;
import com.ua.travel_mate.entities.Users;
import com.ua.travel_mate.repositories.UserRepository;
import com.ua.travel_mate.testData.UsersTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerWithRepositoriesIntegrationTests extends MySQLTestContainer {
    @Autowired
    private UserRepository repository;

    @Test
    void testCreateUser() {
        Users user = UsersTestData.getUser().username("Tad").email("tad@example.com").build();
        Users createdUser = repository.save(user);

        assertNotNull(createdUser);
        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    void testGetUserById() {
        Users user = UsersTestData.getUser().username("jane").email("jane@example.com").build();
        Users createdUser = repository.save(user);

        Users foundUser = repository.findById(createdUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertThat(foundUser.getId()).isEqualTo(createdUser.getId());
    }

    @Test
    void testUpdateUser() {
        Users user = UsersTestData.getUser().username("Alice").email("alice@example.com").build();
        Users createdUser = repository.save(user);

        createdUser.setFirstname("Alice Updated");
        Users updatedUser = repository.save(createdUser);

        assertNotNull(updatedUser);
        assertThat(updatedUser.getFirstname()).isEqualTo("Alice Updated");
    }

    @Test
    void testDeleteUser() {
        Users user = UsersTestData.getUser().username("Bob").email("bob@example.com").build();
        Users createdUser = repository.save(user);

        repository.deleteById(createdUser.getId());
        boolean userExists = repository.existsById(createdUser.getId());

        assertThat(userExists).isFalse();
    }
}
