package nubes.booktify.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nubes.booktify.model.BookOpenLibra;
import nubes.booktify.model.request.CreateBookRequest;

@Service
public class OpenLibraService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  BookService bookService;

  public BookOpenLibra[] getBookOpenLibra(String name, String author) {
    String url = "https://www.etnassoft.com/api/v1/get/?";
    url += name != null ? "book_title=" + name : "";
    url += author != null ? "&book_author=" + author : "";

    ResponseEntity<BookOpenLibra[]> response = restTemplate.getForEntity(url, BookOpenLibra[].class);

    return response.getBody();
  }

  public void getBookOpenLibraById(Integer id) {
    String url = "https://www.etnassoft.com/api/v1/get/?id=" + id;

    ResponseEntity<BookOpenLibra[]> response = restTemplate.getForEntity(url, BookOpenLibra[].class);
    BookOpenLibra[] books = response.getBody();

    CreateBookRequest newBook = new CreateBookRequest();

    Calendar date = Calendar.getInstance();
    date.set(Calendar.YEAR, Integer.parseInt(books[0].getPublisherDate()));
    String datetime = date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DATE);

    newBook.setTitle(books[0].getTitle());
    newBook.setAuthor(books[0].getAuthor());
    newBook.setContent(books[0].getContent());
    newBook.setShortContent(books[0].getContentShort());
    newBook.setPublisher(books[0].getPublisher());
    newBook.setPublisherDate(datetime);
    newBook.setPages(Integer.parseInt(books[0].getPages()));
    newBook.setLanguage(books[0].getLanguage());
    newBook.setUrlDetails("");
    newBook.setUrlDownload("");
    newBook.setCover(books[0].getCover());

    bookService.createBook(newBook);

  }
}
