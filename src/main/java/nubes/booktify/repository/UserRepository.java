package nubes.booktify.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
