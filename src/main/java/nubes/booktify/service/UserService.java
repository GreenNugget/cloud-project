package nubes.booktify.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import nubes.booktify.config.JwtTokenUtil;
import nubes.booktify.exception.CredentialsInvalid;
import nubes.booktify.exception.NotFoundException;
import nubes.booktify.exception.UnprocessableEntity;
import nubes.booktify.model.Jwt;
import nubes.booktify.model.Type;
import nubes.booktify.model.TypeUser;
import nubes.booktify.model.User;
import nubes.booktify.model.request.LoginRequest;
import nubes.booktify.model.request.UserRequest;
import nubes.booktify.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    public User getUserById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);

        if(!user.isPresent()) {
            throw new NotFoundException("El usuario solicitado no existe");
        }

        return user.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if(!user.isPresent()) {
            throw new NotFoundException("El usuario solicitado no existe");
        }

        return user.get();
    }

    @Transactional
    public Jwt loginUser(LoginRequest loginRequest) {
        User loggedUser = this.getUserByEmail(loginRequest.getEmail());

        if(loggedUser == null || loggedUser.equals(null)) {
            throw new CredentialsInvalid("El usuario solicitado no existe");
        }

        String pwd = loginRequest.getPassword();
        String pwdHash = loggedUser.getPassword();

        if(!BCrypt.checkpw(pwd, pwdHash)){
            throw new CredentialsInvalid("La constraseña es incorrecta");
        }

        String token = this.jwtTokenUtil.generateToken(loggedUser);
        loggedUser.setToken(token);

        this.userRepository.save(loggedUser);

        return new Jwt(token);
    }

    @Transactional
    public void logoutUser(User loggedUser) {
        loggedUser.setToken(null);

        this.userRepository.save(loggedUser);
    }

    @Transactional
    public User postUser(UserRequest userRequest) {

        validateEmail(userRequest.getEmail());

        User user = new User();
        user.setEmail(userRequest.getEmail());

        String pwdHash = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        user.setPassword(pwdHash);

        user.setFullname(userRequest.getFullname());
        user.setLastname(userRequest.getLastname());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());

        user.setTypeUser(new TypeUser(3, Type.FREE));

        user = this.userRepository.save(user);

        return user;
    }

    private void validateEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new UnprocessableEntity("El usuario ingresado ya existe");
        }
    }

    @Transactional
    public User putUserById(Integer id, UserRequest userRequest) {
        User user = this.getUserById(id);

        String pwdHash = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        user.setPassword(pwdHash);

        user.setFullname(userRequest.getFullname());
        user.setLastname(userRequest.getLastname());
        user.setUpdated(LocalDateTime.now());

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public void deleteUserById(Integer id) {
        User user = this.getUserById(id);

        //validar si tiene readlist

        this.userRepository.delete(user);
    }
}
