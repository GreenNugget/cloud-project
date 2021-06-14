package nubes.booktify.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.ReadList;

@Repository
public interface ReadListRepository extends CrudRepository<ReadList, Integer> {
  List<ReadList> findByUserId(Integer id);
}
