package nubes.booktify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.ReadListBooks;

@Repository
public interface ReadListsBooksRepository extends CrudRepository<ReadListBooks, Integer> {

}
