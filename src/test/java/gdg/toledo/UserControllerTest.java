package gdg.toledo;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(
      DockerImageName.parse("postgres:16.1")
  );

  @LocalServerPort
  private int port;

  private RestClient restClient;

  @BeforeEach
  void init() {
    this.restClient = RestClient.create("http://localhost:%s".formatted(port));
  }

  @Test
  void createUser() {
    ResponseEntity<User> response = restClient.post()
        .uri("/users")
        .body(new User("Iván"))
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(User.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).extracting("name").isEqualTo("Iván");

  }



}
