package com.ua.travel_mate;

import com.ua.travel_mate.entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static final MySQLContainer MY_SQL_CONTAINER;


    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:8");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void testCreateUser() {
        Users user = Users.builder().username("john_doe").email("john.doe@example.com").passwordHash("hashed_password")
                .firstname("John").lastname("Doe").profilePicture("profile.jpg").bio("Software Developer").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
        String createUrl = "http://localhost:" + port + "/api/users";
        ResponseEntity<Users> response = restTemplate.postForEntity(createUrl, user, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testGetUserById() {
        Users user = Users.builder().username("jane_doe").email("jane.doe@example.com").passwordHash("hashed_password")
                .firstname("Jane").lastname("Doe").profilePicture("profile.jpg").bio("Product Manager").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
        Users createdUser = createUser(user);

        String getUrl = "http://localhost:" + port + "/api/users/" + createdUser.getId();
        ResponseEntity<Users> response = restTemplate.getForEntity(getUrl, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(createdUser.getId());
    }

    @Test
    void testUpdateUser() {
        Users user = Users.builder().username("alice_smith").email("alice.smith@example.com").passwordHash("hashed_password")
                .firstname("Alice")
                .lastname("Smith").profilePicture("profile.jpg").bio("UX Designer").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
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
        Users user = Users.builder().username("bob_jones").email("bob.jones@example.com").passwordHash("hashed_password")
                .firstname("Bob").lastname("Jones").profilePicture("profile.jpg").bio("Marketing Specialist").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();

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
