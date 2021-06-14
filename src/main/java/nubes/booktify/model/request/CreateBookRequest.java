package nubes.booktify.model.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;

    @NotEmpty
    private String shortContent;

    @NotEmpty
    private String publisher;

    private String publisherDate;

    private Integer pages;

    @NotEmpty
    private String language;

    @NotEmpty
    private String urlDetails;

    @NotEmpty
    private String urlDownload;

    @NotEmpty
    private String cover;

    public CreateBookRequest(String title,String author, String content, String shortContent, String publisher, String publisherDate, Integer pages, String language, String urlDetails, String urlDownload, String cover) {
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

    public CreateBookRequest title(String title){
        this.title = title;
        return this;
    }

    public CreateBookRequest author(String author) {
        this.author = author;
        return this;
    }

    public CreateBookRequest pages(Integer pages) {
        this.pages = pages;
        return this;
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
