package nubes.booktify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{
    Optional<Book> findById(Integer id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    void deleteById(Integer id);
}
