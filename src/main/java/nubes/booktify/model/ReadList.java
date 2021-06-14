package nubes.booktify.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nubes.booktify.model.request.ReadListRequest;

@Entity
@Table(name = "readlist")
public class ReadList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "readlist_id")
  private Integer id;

  @Column(name = "user_id")
  @JsonIgnore
  private Integer userId;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "readList")
  private List<ReadListBooks> readListsBooks;

  public ReadList(Integer id, Integer userId, String name) {
    this.id = id;
    this.userId = userId;
    this.name = name;
  }

  public ReadList() {
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

  public List<ReadListBooks> getReadListsBooks() {
    return readListsBooks;
  }

  public void setReadListsBooks(List<ReadListBooks> readListsBooks) {
    this.readListsBooks = readListsBooks;
  }

  public ReadList setReadList(ReadListRequest request) {
    this.name = request.getName();
    this.userId = request.getUserId();
    return this;
  }

  public ReadList updateReadList(ReadListRequest request) {
    this.name = request.getName();
    return this;
  }

}