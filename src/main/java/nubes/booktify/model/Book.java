package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Integer bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "content_short")
    private String shortContent;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publisher_date")
    private String publisherDate;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "language")
    private String language;

    @Column(name = "url_datails")
    private String urlDetails;

    @Column(name = "url_downland")
    private String urlDownload;

    @Column(name = "cover")
    private String cover;

    /*public Book(Integer id, String title,String author, String content, String shortContent, String publisher, String publisherDate, Integer pages, String language, String urlDetails, String urlDownload, String cover) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.shortContent = shortContent;
        this.publisher = publisher;
    }*/

    public Book bookId(Integer id) {
        this.bookId = id;
        return this;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }
}
