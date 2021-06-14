package nubes.booktify.model.request;

import javax.validation.constraints.NotEmpty;

public class UpdateBookRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;

    public UpdateBookRequest(String title,String author,String content){
        this.title = title;
        this.author = author;
        this.content = content;
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

}
