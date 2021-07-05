package nubes.booktify.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.BookOpenLibra;
import nubes.booktify.service.OpenLibraService;

@RestController
@RequestMapping("/api/v1")
public class OpenLibraRest {

  @Autowired
  private OpenLibraService openLibraService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/openlibra/libros")
  public ResponseEntity<BookOpenLibra[]> getLibrosOpenLibra(@RequestParam(name = "name") String bookName,
      @RequestParam(name = "author", required = false) String author) {
    BookOpenLibra[] books = openLibraService.getBookOpenLibra(bookName, author);
    return ResponseEntity.ok().body(books);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/openlibra/libros/{id}")
  public ResponseEntity<BookOpenLibra[]> postLibrosOpenLibra(@PathVariable(name = "id") Integer bookId) {
    openLibraService.getBookOpenLibraById(bookId);
    return ResponseEntity.ok().build();
  }
}
