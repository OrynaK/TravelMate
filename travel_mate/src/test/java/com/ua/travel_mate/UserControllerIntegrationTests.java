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
public class UserControllerIntegrationTests extends MySQLTestContainer {

    @Autowired
    private UserRepository repository;

    @Test
    void testCreateUser() {
        Users user = UsersTestData.getUser().username("Tad").email("tad@example.com").build();
        String createUrl = "http://localhost:" + port + "/api/users";
        ResponseEntity<Users> response = restTemplate.postForEntity(createUrl, user, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testGetUserById() {
        Users user = UsersTestData.getUser().username("jane").email("jane@example.com").build();
        Users createdUser = createUser(user);

        String getUrl = "http://localhost:" + port + "/api/users/" + createdUser.getId();
        ResponseEntity<Users> response = restTemplate.getForEntity(getUrl, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(createdUser.getId());
    }

    @Test
    void testUpdateUser() {
        Users user = UsersTestData.getUser().username("Alice").email("alice@example.com").build();
        Users createdUser = createUser(user);

        createdUser.setFirstname("Alice Updated");
        HttpEntity<Users> requestUpdate = new HttpEntity<>(createdUser);
        String updateUrl = "http://localhost:" + port + "/api/users/" + createdUser.getId();
        ResponseEntity<Users> response = restTemplate.exchange(updateUrl, HttpMethod.PUT, requestUpdate, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstname()).isEqualTo("Alice Updated");
    }

    @Test
    void testDeleteUser() {
        Users user = UsersTestData.getUser().username("Bob").email("bob@example.com").build();

        String deleteUrl = "http://localhost:" + port + "/api/users/" + createUser(user).getId();
        ResponseEntity<Void> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    private Users createUser(Users user) {
        String createUrl = "http://localhost:" + port + "/api/users";
        Users createdUser = restTemplate.postForEntity(createUrl, user, Users.class).getBody();
        assertNotNull(createdUser);
        return createdUser;
    }
}
