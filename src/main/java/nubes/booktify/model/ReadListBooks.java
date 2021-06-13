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
}
