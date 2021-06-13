package nubes.booktify.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.Book;
import nubes.booktify.model.request.BookRequest;
import nubes.booktify.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookRest {
    
    @Autowired
    private BookService bookService;

    @GetMapping("/libros")
    public ResponseEntity<List<Book>> getLibros() {
        return ResponseEntity.ok().body(bookService.getBooks());
    }

    @PostMapping("/libros")
    public ResponseEntity<Book> crearLibro(@RequestBody @Valid BookRequest request)
            throws URISyntaxException {

        Book book = bookService.createBook(request);

        return ResponseEntity.created(new URI("/libros/" + book.getId())).body(book);
    }
}
