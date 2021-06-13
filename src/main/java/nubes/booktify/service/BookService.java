package nubes.booktify.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.model.Book;
import nubes.booktify.model.request.BookRequest;
import nubes.booktify.repository.BookRepository;

@Service
public class BookService {

    @Autowired BookRepository bookRepository;

    public List<Book> getBooks(){
        List<Book> books = new LinkedList<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    public Optional<Book> searchBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
/*
    public List<Book> searchBookByTitleAuthorDate(){

    }*/
    @Transactional
    public Book createBook(BookRequest bookReq){
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

        return book;
    }
    
}
