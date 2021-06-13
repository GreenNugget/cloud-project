package nubes.booktify.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nubes.booktify.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{
    List<Book> findByBookId(Integer id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisherDate(String date);
    void deleteById(Integer id);
}
