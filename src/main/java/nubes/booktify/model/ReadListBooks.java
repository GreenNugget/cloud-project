package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "readlists_books")
@JsonIgnoreProperties({ "readlist.book" })
public class ReadListBooks {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "readlist_book_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "readlist_id")
  private ReadList readList;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  public ReadListBooks(ReadList readList, Book book) {
    this.readList = readList;
    this.book = book;
  }

  public ReadListBooks() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReadList getReadList() {
    return readList;
  }

  public void setReadList(ReadList readList) {
    this.readList = readList;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

}
