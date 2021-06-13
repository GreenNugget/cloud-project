package nubes.booktify.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

/*import org.graalvm.compiler.hotspot.nodes.PluginFactory_BeginLockScopeNode;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.model.Book;
import nubes.booktify.model.request.CreateBookRequest;
import nubes.booktify.model.request.UpdateBookRequest;
import nubes.booktify.repository.BookRepository;

@Service
public class BookService {

    @Autowired BookRepository bookRepository;

    public List<Book> getBooks(){
        List<Book> books = new LinkedList<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    public List<Book> searchBookById(Integer id) {
        //Integer bookId = Integer.parseInt(id);
        return bookRepository.findByBookId(id);
    }

    public List<Book> searchBook(String title,String author, String date) {
        List<Book> foundBooks = new LinkedList<>();

        bookRepository.findByTitle(title).iterator().forEachRemaining(foundBooks::add);
        bookRepository.findByAuthor(author).iterator().forEachRemaining(foundBooks::add);
        bookRepository.findByPublisherDate(date).iterator().forEachRemaining(foundBooks::add);

        return foundBooks;
    }
    
    @Transactional
    public Book createBook(CreateBookRequest bookReq){
        Book book = new Book();

        book.setTitle(bookReq.getTitle());
        book.setAuthor(bookReq.getAuthor());
        book.setContent(bookReq.getContent());
        book.setShortContent(bookReq.getShortContent());
        book.setPublisher(bookReq.getPublisher());

/*
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = date.format(formater);*/

        book.setPublisherDate(bookReq.getPublisherDate());
        book.setPages(bookReq.getPages());
        book.setLanguage(bookReq.getLanguage());
        book.setUrlDetails(bookReq.getUrlDetails());
        book.setUrlDownload(bookReq.getUrlDownload());
        book.setCover(bookReq.getCover());
        book = bookRepository.save(book);

        return book;
    }
    
    @Transactional
    public Book deleteBook(Integer bookId){
        Book deletedBook = bookRepository.findByBookId(bookId).get(0);

        bookRepository.deleteById(deletedBook.getBookId());;
        return deletedBook;
    }

    @Transactional
    public Book updateBook(String title, UpdateBookRequest updatedBook){
        Book updBook = bookRepository.findByTitle(title).get(0);

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