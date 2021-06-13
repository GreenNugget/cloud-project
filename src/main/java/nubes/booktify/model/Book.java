package nubes.booktify.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Integer id;

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

    public Book id(Integer id) {
        this.id = id;
        return this;
    }

    public Book nombre(String title) {
        this.title = title;
        return this;
    }
}
