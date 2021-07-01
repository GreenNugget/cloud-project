package nubes.booktify.service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.model.Book;
import nubes.booktify.model.article.BookIndex;
import nubes.booktify.model.request.CreateBookRequest;
import nubes.booktify.model.request.UpdateBookRequest;
import nubes.booktify.repository.BookRepository;
import nubes.booktify.repository.Elasticsearch.BookIndexRepository;

@Service
public class BookService {

    @Autowired 
    BookRepository bookRepository;

    @Autowired
    BookIndexRepository bookIndexRepository;

    public List<Book> getBooks(){
        List<Book> books = new LinkedList<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    public List<Book> searchBookById(Integer id) {
        return bookRepository.findByBookId(id);
    }

    public List<Book> searchBook(String title,String author, String date) {
        List<Book> foundBooks = new LinkedList<>();

        bookRepository.findByTitle(title).iterator().forEachRemaining(foundBooks::add);
        bookRepository.findByAuthor(author).iterator().forEachRemaining(foundBooks::add);
        bookRepository.findByPublisherDate(date).iterator().forEachRemaining(foundBooks::add);

        List<Book> noRepeat = foundBooks.stream().distinct().collect(Collectors.toList());

        return noRepeat;
    }
    
    @Transactional
    public Book createBook(CreateBookRequest bookReq){
        Book book = new Book();

        book.setTitle(bookReq.getTitle());
        book.setAuthor(bookReq.getAuthor());
        book.setContent(bookReq.getContent());
        book.setShortContent(bookReq.getShortContent());
        book.setPublisher(bookReq.getPublisher());
        book.setPublisherDate(bookReq.getPublisherDate());
        book.setPages(bookReq.getPages());
        book.setLanguage(bookReq.getLanguage());
        book.setUrlDetails(bookReq.getUrlDetails());
        book.setUrlDownload(bookReq.getUrlDownload());
        book.setCover(bookReq.getCover());
        book = bookRepository.save(book);

        this.indexarLibro(bookReq);

        return book;
    }

    @Transactional
    private void indexarLibro(CreateBookRequest bookRequest) {
        BookIndex bookIndex = new BookIndex();

        bookIndex.setAuthor(bookRequest.getAuthor());
        bookIndex.setContent(bookRequest.getContent());
        bookIndex.setLanguage(bookRequest.getLanguage());
        bookIndex.setPages(bookRequest.getPages());
        bookIndex.setPublisher(bookRequest.getPublisher());
        bookIndex.setPublisherDate(LocalDateTime.now()); //cambiar la entidad libro para que sea Date y no String
        bookIndex.setTitle(bookIndex.getTitle());

        this.bookIndexRepository.save(bookIndex);
    }
    
    @Transactional
    public Book deleteBook(Integer bookId){
        Book deletedBook = bookRepository.findByBookId(bookId).get(0);

        bookRepository.deleteById(deletedBook.getBookId());;
        return deletedBook;
    }

    @Transactional
    public Book updateBook(Integer bookId, UpdateBookRequest updatedBook){
        Book updBook = bookRepository.findByBookId(bookId).get(0);//findByTitle(title).get(0);

        Book bookReference = bookRepository.findByTitle(updBook.getTitle()).get(0);
        bookReference = setBookNewValues(bookReference, updatedBook);
        return bookRepository.save(bookReference);
    }

    private Book setBookNewValues(Book bookReference, UpdateBookRequest updateRequest) {
        bookReference.setTitle(updateRequest.getTitle());
        bookReference.setAuthor(updateRequest.getAuthor());
        bookReference.setContent(updateRequest.getContent());
        return bookReference;
    }
}