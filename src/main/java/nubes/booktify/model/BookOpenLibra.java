package nubes.booktify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookOpenLibra {

  @JsonProperty("ID")
  private String id;

  private String title;
  private String author;
  private String content;

  @JsonProperty("content_short")
  private String contentShort;

  private String publisher;

  @JsonProperty("publisher_date")
  private String publisherDate;

  private String pages;

  private String language;

  @JsonProperty("url_download")
  private String urlDownload;
  private String cover;
  private String thumbnail;

  public BookOpenLibra() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getContentShort() {
    return contentShort;
  }

  public void setContentShort(String contentShort) {
    this.contentShort = contentShort;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPublisherDate() {
    return publisherDate;
  }

  public void setPublisherDate(String publisherDate) {
    this.publisherDate = publisherDate;
  }

  public String getPages() {
    return pages;
  }

  public void setPages(String pages) {
    this.pages = pages;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getUrlDownload() {
    return urlDownload;
  }

  public void setUrlDownload(String urlDownload) {
    this.urlDownload = urlDownload;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

}
