package nubes.booktify.rest;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.Jwt;
import nubes.booktify.model.User;
import nubes.booktify.model.request.LoginRequest;
import nubes.booktify.model.request.UpdateTypeUserRequest;
import nubes.booktify.model.request.UserRequest;
import nubes.booktify.service.UserService;
import nubes.booktify.model.Type;

@RestController
@RequestMapping("/api/v1")
public class UserRest {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<Jwt> getJwt(@RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody @Valid LoginRequest loginRequest) {
        Jwt jwt = this.userService.loginUser(loginRequest,userAgent);

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = this.userService.getUserById(id);
        
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/register/users")
    public ResponseEntity<User> postUser(@RequestBody @Valid UserRequest userRequest) {
        User user = this.userService.postUser(userRequest, Type.FREE);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/admins")
    public ResponseEntity<User> postAdmin(@RequestBody @Valid UserRequest userRequest) {
        User user = this.userService.postUser(userRequest, Type.ADMIN);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
        
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/{id}/roles")
    public ResponseEntity<User> putRole(@PathVariable Integer id, @RequestBody @Valid UpdateTypeUserRequest updateType) throws IOException {
        User user = this.userService.putTypeUser(id, updateType.getType());

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> putUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Integer id ) throws IOException {
        User user = this.userService.putUserById(id, userRequest);
        
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws IOException {
        this.userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
