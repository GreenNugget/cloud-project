package nubes.booktify.service;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import nubes.booktify.model.Book;
import nubes.booktify.model.document.BookIndex;
import nubes.booktify.model.request.CreateBookRequest;
import nubes.booktify.model.request.UpdateBookRequest;
import nubes.booktify.repository.BookRepository;
import nubes.booktify.repository.elasticsearch.BookIndexRepository;

@Service
public class BookService {

    @Autowired 
    BookRepository bookRepository;

    @Autowired
    BookIndexRepository bookIndexRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    public List<Book> getBooks(){
        List<Book> books = new LinkedList<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    public List<Book> getAllBookFromElastic(List<BookIndex> indexs) {
        List<Book> listaLibros = new LinkedList<>();

        this.bookRepository.findAllById(
            indexs.stream().map(BookIndex::getId)
            .collect(Collectors.toList())
        ).iterator()
        .forEachRemaining(listaLibros::add);

        return listaLibros;
    }

    public List<Book> searchBookById(Integer id) {
        return bookRepository.findByBookId(id);
    }
    
    public List<Book> searchBook(String query) {
        QueryBuilder queryBuilder = 
        (QueryBuilder) QueryBuilders.queryStringQuery(query);
    
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(queryBuilder).build();

        SearchHits<BookIndex> bookHits =
        elasticsearchOperations
        .search(searchQuery, BookIndex.class, IndexCoordinates.of("biblioteca"));

        List<BookIndex> listaIndexs = bookHits.get().map(SearchHit::getContent).collect(Collectors.toList());

        return this.getAllBookFromElastic(listaIndexs);
    }

    public List<Book> filteredSearchBook(String query, String filter) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
        .must(QueryBuilders.queryStringQuery(query))
        .filter(QueryBuilders.simpleQueryStringQuery(filter).field("language"));
    
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(boolQuery).build();

        SearchHits<BookIndex> bookHits =
        elasticsearchOperations
        .search(searchQuery, BookIndex.class, IndexCoordinates.of("biblioteca"));

        List<BookIndex> listaIndexs = bookHits.get().map(SearchHit::getContent).collect(Collectors.toList());

        return this.getAllBookFromElastic(listaIndexs);
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

        this.indexarLibro(book);

        return book;
    }

    @Transactional
    private void indexarLibro(Book book) {
        BookIndex bookIndex = new BookIndex();

        bookIndex.setId(book.getBookId());
        bookIndex.setAuthor(book.getAuthor());
        bookIndex.setContent(book.getContent());
        bookIndex.setLanguage(book.getLanguage());
        bookIndex.setPages(book.getPages());
        bookIndex.setPublisher(book.getPublisher());
        bookIndex.setTitle(book.getTitle());

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