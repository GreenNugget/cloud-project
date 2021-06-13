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

    @NotEmpty
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

    /*public BookRequest(String title, String author, Integer pages){
        this.title = title;
        this.author = author;
        this.pages = pages;
    }*/
/*
    public BookRequest title(String title){
        this.title = title;
        return this;
    }

    public BookRequest author(String author) {
        this.author = author;
        return this;
    }

    public BookRequest pages(Integer pages) {
        this.pages = pages;
        return this;
    }    */
}
