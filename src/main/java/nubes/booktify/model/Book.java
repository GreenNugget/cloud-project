package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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

    public Book(){}

    public Book(Integer bookId, String title,String author, String content, String shortContent, String publisher, String publisherDate, Integer pages, String language, String urlDetails, String urlDownload, String cover) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.shortContent = shortContent;
        this.publisher = publisher;
        this.publisherDate = publisherDate;
        this.pages = pages;
        this.language = language;
        this.urlDetails = urlDetails;
        this.urlDownload = urlDownload;
        this.cover = cover;
    }

    public Book bookId(Integer id) {
        this.bookId = id;
        return this;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }


    /**
     * @return Integer return the bookId
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return String return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return String return the shortContent
     */
    public String getShortContent() {
        return shortContent;
    }

    /**
     * @param shortContent the shortContent to set
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    /**
     * @return String return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return String return the publisherDate
     */
    public String getPublisherDate() {
        return publisherDate;
    }

    /**
     * @param publisherDate the publisherDate to set
     */
    public void setPublisherDate(String publisherDate) {
        this.publisherDate = publisherDate;
    }

    /**
     * @return Integer return the pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return String return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return String return the urlDetails
     */
    public String getUrlDetails() {
        return urlDetails;
    }

    /**
     * @param urlDetails the urlDetails to set
     */
    public void setUrlDetails(String urlDetails) {
        this.urlDetails = urlDetails;
    }

    /**
     * @return String return the urlDownload
     */
    public String getUrlDownload() {
        return urlDownload;
    }

    /**
     * @param urlDownload the urlDownload to set
     */
    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }

    /**
     * @return String return the cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover the cover to set
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

}
