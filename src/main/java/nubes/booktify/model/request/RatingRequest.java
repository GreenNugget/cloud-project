package nubes.booktify.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RatingRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer score;

    @NotEmpty
    @NotNull
    private String comment;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}