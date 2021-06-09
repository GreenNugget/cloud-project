package nubes.booktify.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.Jwt;
import nubes.booktify.model.User;
import nubes.booktify.model.request.LoginRequest;
import nubes.booktify.model.request.UserRequest;
import nubes.booktify.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserRest {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<Jwt> getJwt(@RequestBody @Valid LoginRequest loginRequest) {
        Jwt jwt = this.userService.loginUser(loginRequest);

        return ResponseEntity.ok().body(jwt);
    }

    @GetMapping("/auth/self")
    public ResponseEntity<User> getLoggedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logoutUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.userService.logoutUser(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = this.userService.getUserById(id);
        
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody @Valid UserRequest userRequest) {
        User user = this.userService.postUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> putUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Integer id ) {
        User user = this.userService.putUserById(id, userRequest);
        
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        this.userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
