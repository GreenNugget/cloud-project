package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "readlist")
public class ReadList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "readlist_id")
  private Integer id;

  @NotNull
  @Column(name = "user_id")
  private Integer userId;

  @NotEmpty
  @Column(name = "name")
  private String name;

  public ReadList(Integer id, Integer userId, String name) {
    this.id = id;
    this.userId = userId;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}