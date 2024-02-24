package gdg.toledo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  List<User> listAllUsers() {
    return userService.listAll();
  }

  @GetMapping("/users/{id}")
  ResponseEntity<User> findUserById(@PathVariable Long id) {
    return userService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  User createNewUser(@RequestBody User user) {
    return userService.saveUser(user);
  }
}
