package nubes.booktify.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer>{
    Optional<List<Rating>> findByUserUserId(Integer userId);
}