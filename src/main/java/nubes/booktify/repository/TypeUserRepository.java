package nubes.booktify.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.Type;
import nubes.booktify.model.TypeUser;

@Repository
public interface TypeUserRepository extends CrudRepository<TypeUser, Integer> {
    
    Optional<TypeUser> findByType(Type type);
}
