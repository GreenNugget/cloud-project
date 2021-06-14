package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "readlists_books")
public class ReadListBooks {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "readlist_book_id")
  private Integer id;

  @Column(name = "readlist_id")
  private Integer readListId;

  @Column(name = "book_id")
  private Integer bookId;

  public ReadListBooks(Integer readListId, Integer bookId) {
    this.readListId = readListId;
    this.bookId = bookId;
  }

  public ReadListBooks() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
