package nubes.booktify.model.request;

import javax.validation.constraints.NotNull;

public class ReadlistBookRequest {

  @NotNull
  private Integer readListId;

  @NotNull
  private Integer bookId;

  public ReadlistBookRequest(@NotNull Integer readListId, @NotNull Integer bookId) {
    this.readListId = readListId;
    this.bookId = bookId;
  }

  public ReadlistBookRequest() {
  }

  public Integer getReadListId() {
    return readListId;
  }

  public void setReadListId(Integer readListId) {
    this.readListId = readListId;
  }

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }
}
