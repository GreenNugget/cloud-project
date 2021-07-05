package nubes.booktify.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.Book;
import nubes.booktify.model.request.CreateBookRequest;
import nubes.booktify.model.request.UpdateBookRequest;
import nubes.booktify.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookRest {

    @Autowired
    private BookService bookService;

    @GetMapping("/libros") // All books
    public ResponseEntity<List<Book>> getLibros() {
        return ResponseEntity.ok().body(bookService.getBooks());
    }

    @GetMapping("/libros/{id}") // Search by id
    public ResponseEntity<List<Book>> buscarPorId(@RequestParam("id") Integer bookId) {
        return ResponseEntity.ok().body(bookService.searchBookById(bookId));
    }

    @GetMapping("/libros/busqueda/completa") //Search by word or sentence
    public ResponseEntity< List<Book> > buscarCoincidencia(@RequestParam("q") String query) {
        List<Book> listaLibros = this.bookService.searchBook(query);

        return ResponseEntity.ok().body(listaLibros);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/libros") // Create new book
    public ResponseEntity<Book> crearLibro(@RequestBody @Valid CreateBookRequest request) throws URISyntaxException {

        Book book = bookService.createBook(request);

        return ResponseEntity.created(new URI("/libros/" + book.getBookId())).body(book);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/libros/{id}") // Update a book
    public ResponseEntity<Book> editarPorfesor(@PathVariable("id") Integer bookId,
            @RequestBody UpdateBookRequest bookReq) {
        return ResponseEntity.ok().body(bookService.updateBook(bookId, bookReq));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/libros/{id}") // Delete book
    public ResponseEntity<Book> eliminarLibro(@PathVariable("id") Integer bookId) {
        return ResponseEntity.ok().body(bookService.deleteBook(bookId));
    }

}
