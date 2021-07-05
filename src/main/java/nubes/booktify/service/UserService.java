package nubes.booktify.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
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
import nubes.booktify.model.Rating;
import nubes.booktify.model.ReadList;
import nubes.booktify.model.Type;
import nubes.booktify.model.TypeUser;
import nubes.booktify.model.User;
import nubes.booktify.model.request.LoginRequest;
import nubes.booktify.model.request.UserRequest;
import nubes.booktify.repository.RatingRepository;
import nubes.booktify.repository.ReadListRepository;
import nubes.booktify.repository.TypeUserRepository;
import nubes.booktify.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ReadListRepository readListRepository;

    @Autowired
    TypeUserRepository typeUserRepository;

    @Autowired
    private EmailService emailService;

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
    public Jwt loginUser(LoginRequest loginRequest,String userAgent) {
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

        try {
            emailService.loginAlert(loginRequest.getEmail(), userAgent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Jwt(token);
    }

    @Transactional
    public void logoutUser(User loggedUser) {
        loggedUser.setToken(null);

        this.userRepository.save(loggedUser);
    }

    @Transactional
    public User postUser(UserRequest userRequest, Type type) {

        validateEmail(userRequest.getEmail());

        User user = new User();
        user.setEmail(userRequest.getEmail());

        String pwdHash = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        user.setPassword(pwdHash);

        user.setFullname(userRequest.getFullname());
        user.setLastname(userRequest.getLastname());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());

        TypeUser typeUser = this.validateTypeUser(type);
        user.setTypeUser(typeUser);

        try {
            emailService.WelcomeEmail(user.getEmail(), user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user = this.userRepository.save(user);

        return user;
    }

    private TypeUser validateTypeUser(Type type) {
        Optional<TypeUser> typeUser = this.typeUserRepository.findByType(type);

        if(!typeUser.isPresent()) {
            throw new NotFoundException("El tipo de usuario no existe.");
        }

        return typeUser.get();
    }

    private void validateEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new UnprocessableEntity("El usuario ingresado ya existe");
        }
    }

    @Transactional
    public User putUserById(Integer id, UserRequest userRequest) throws IOException {
        User user = this.getUserById(id);

        String pwdHash = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        user.setPassword(pwdHash);

        emailService.editAlert(user.getEmail(), user, userRequest);

        user.setFullname(userRequest.getFullname());
        user.setLastname(userRequest.getLastname());
        user.setUpdated(LocalDateTime.now());
        user.setEmail(userRequest.getEmail());

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User putTypeUser(Integer id, Type type) throws IOException {
        Optional<User> uOptional = this.userRepository.findById(id);

        if(!uOptional.isPresent()) {
            throw new NotFoundException("El usuario solicitado no existe.");
        }

        User user = uOptional.get();
        TypeUser typeUser = this.validateTypeUser(type);
        user.setTypeUser(typeUser);
        user.setUpdated(LocalDateTime.now());

        emailService.changeUserTypeAlert(user.getEmail(), typeUser);

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public void deleteUserById(Integer id) throws IOException {
        User user = this.getUserById(id);

        List<ReadList> readlists = this.readListRepository.findByUserId(id);

        if(readlists != null) {
            this.readListRepository.deleteAll(readlists);
        }

        Optional<List<Rating>> ratings = this.ratingRepository.findByUserUserId(id);

        if(ratings.isPresent()) {
            this.ratingRepository.deleteAll(ratings.get());
        }

        emailService.deletedUserAlert(user.getEmail(), user);
        this.userRepository.delete(user);
    }
}
